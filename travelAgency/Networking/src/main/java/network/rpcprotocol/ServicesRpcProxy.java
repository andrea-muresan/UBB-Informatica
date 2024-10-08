package network.rpcprotocol;

import app.model.Flight;
import app.model.User;
import app.model.dto.DtoUtils;
import app.model.dto.SearchFlightsDto;
import app.model.dto.TicketPurchaseDto;
import app.model.dto.UserDto;
import services.IObserver;
import services.IServices;
import services.MyException;




import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesRpcProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;


    public ServicesRpcProxy(String host, int port){
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
//        initializeConnection();
    }

    @Override
    public void findUserByCredentials(String username, String password, IObserver userObs) throws MyException {
        initializeConnection();
        UserDto userDto = new UserDto(username, password);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(userDto).build();
        sendRequest(request);

        Response response = readResponse();
        if(response.type() == ResponseType.OK){
            this.client = userObs;
            return;
        }
        if(response.type() == ResponseType.ERROR){
            String err = response.data().toString();
            closeConnection();
            throw new MyException(err);
        }
    }

    @Override
    public boolean signUp(String username, String password) {
        return false;
    }

    @Override
    public Iterable<Flight> getAllFlights() throws MyException {
        Request request = new Request.Builder().type(RequestType.GET_FLIGHTS).build();
        sendRequest(request);

        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
//            closeConnection();
            String err = response.data().toString();
            throw new MyException(err);
        }
        List<Flight> flights = (List<Flight>)response.data();
        return flights;
    }

    @Override
    public Iterable<Flight> getFlightsByDestinationDate(String dest, LocalDate date) throws MyException {
        SearchFlightsDto flDto = new SearchFlightsDto(dest, date);
        Request request = new Request.Builder().type(RequestType.GET_FLIGHTS_DEST_DATE).data(flDto).build();
        sendRequest(request);

        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
//            closeConnection();
            String err = response.data().toString();
            throw new MyException(err);
        }
        List<Flight> flights = (List<Flight>)response.data();
        return flights;
    }

    @Override
    public void buyTicket(Flight flight, String client, String address, String tourists, String noSeats) throws MyException {
        TicketPurchaseDto ticketPurchase = new TicketPurchaseDto(flight, noSeats, client, tourists, address);
        Request request = new Request.Builder().type(RequestType.PURCHASE).data(ticketPurchase).build();
        sendRequest(request);
        Response response = readResponse();
        if(response.type() == ResponseType.ERROR){
//            closeConnection();
            String err = response.data().toString();
            throw new MyException(err);
        }
    }

    @Override
    public void logOut(User user, IObserver userObs) throws MyException {
        UserDto empDto = DtoUtils.getDTO(user);
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(empDto).build();
        sendRequest(request);

        Response response = readResponse();
        closeConnection();
        if(response.type() == ResponseType.ERROR)
            throw new MyException(response.data().toString());

    }




    private void sendRequest(Request request)throws MyException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new MyException("Error sending object " + e);
        }
    }

    private Response readResponse() throws MyException {
        Response response = null;
        try{
            response = qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void handleUpdate(Response response) throws IOException {
        if (response.type()== ResponseType.FLIGHT) {
            System.out.println("handle update in proxy");
            Flight fl = (Flight) response.data();
            client.updateFlight(fl);
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

    private void initializeConnection() {
        try {
            connection = new Socket(host,port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.FLIGHT;
    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Reading error " + e);
                }
            }
        }
    }
}