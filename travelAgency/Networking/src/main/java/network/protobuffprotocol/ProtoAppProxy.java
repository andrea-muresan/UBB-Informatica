package network.protobuffprotocol;

import app.model.Flight;

import app.model.User;
import services.IObserver;
import services.IServices;
import services.MyException;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoAppProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<AppProtobuffs.Response> qresponses;
    private volatile boolean finished;

    public ProtoAppProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<AppProtobuffs.Response>();
    }

    private void handleUpdate(AppProtobuffs.Response response) throws IOException {
        if(response.getType() == AppProtobuffs.Response.Type.FLIGHT){
            System.out.println("Flight updated in proxy");
            client.updateFlight(ProtoUtils.getFlight(response));

        }
    }

    private void sendRequest(AppProtobuffs.Request request) throws MyException {
        try{
            System.out.println("Sending request... " + request);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new MyException("Error sending object " + e);
        }
    }

    private AppProtobuffs.Response readResponse() throws MyException {
        AppProtobuffs.Response response = null;
        try{
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() {
        try{
            connection = new Socket(host, port);
            output = connection.getOutputStream();
            input = connection.getInputStream();
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread th = new Thread(new ReaderThread());
        th.start();
    }

    @Override
    public void findUserByCredentials(String username, String password, IObserver userObs) throws MyException {
        initializeConnection();
        System.out.println("Login request...");
        sendRequest(ProtoUtils.createLoginRequest(username, password));

        AppProtobuffs.Response response = readResponse();
        if(response.getType() == AppProtobuffs.Response.Type.OK){
            this.client = userObs;
            return;
        }
        if(response.getType() == AppProtobuffs.Response.Type.ERROR){
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new MyException(errorText);
        }
    }

    @Override
    public boolean signUp(String username, String password) {
        return false;
    }

    @Override
    public Iterable<Flight> getAllFlights() throws MyException {
        sendRequest(ProtoUtils.createFindAllFlightsRequest());

        AppProtobuffs.Response response = readResponse();
        if(response.getType() == AppProtobuffs.Response.Type.ERROR){
            String errorText = response.getError();
            throw new MyException(errorText);
        }
        Iterable<Flight> flights = ProtoUtils.getAllFlights(response);
        return flights;
    }

    @Override
    public Iterable<Flight> getFlightsByDestinationDate(String dest, LocalDate date) throws MyException {
        String dateStr = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        sendRequest(ProtoUtils.createFindFlightsDestDateRequest(dest, dateStr));

        AppProtobuffs.Response response = readResponse();
        if(response.getType() == AppProtobuffs.Response.Type.ERROR){
            String errorText = response.getError();
            throw new MyException(errorText);
        }
        Iterable<Flight> flights = ProtoUtils.getAllFlights(response);
        return flights;
    }

    @Override
    public void buyTicket(Flight flight, String client, String address, String tourists, String noSeats) throws MyException, SQLException {
        sendRequest(ProtoUtils.createPurchaseRequest(flight, client, address, tourists, noSeats));

        AppProtobuffs.Response response = readResponse();
        if(response.getType() == AppProtobuffs.Response.Type.ERROR){
            // closeConnection();
            String errorText = ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
    }

    @Override
    public void logOut(User user, IObserver userObs) throws MyException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        AppProtobuffs.Response response = readResponse();
        closeConnection();

        if(response.getType() == AppProtobuffs.Response.Type.ERROR){
            String errorText = ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
    }

    private class ReaderThread implements Runnable{
        @Override
        public void run() {
            while(!finished){
                try{
                    AppProtobuffs.Response response = AppProtobuffs.Response.parseDelimitedFrom(input);
                    System.out.println("Response received " + response);

                    if(isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }
                    else{
                        try{
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    private boolean isUpdateResponse(AppProtobuffs.Response.Type type){
        return type == AppProtobuffs.Response.Type.FLIGHT;
    }
}
