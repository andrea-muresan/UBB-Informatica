package app.network.rpcprotocol;

import app.model.Flight;
import app.services.IObserver;
import app.services.IServices;

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
        qresponses = new LinkedBlockingQueue<>();
    }

    // TODO: mda
    @Override
    public boolean findUserByCredentials(String username, String password) {
        return false;
    }

    @Override
    public boolean signUp(String username, String password) {
        return false;
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return null;
    }

    @Override
    public Iterable<Flight> getFlightsByDestinationDate(String dest, LocalDate date) {
        return null;
    }

    @Override
    public String buyTicket(Flight flight, String client, String address, String tourists, String noSeats) {
        return null;
    }

//    @Override
//    public void logIn(String username, String password, IObserver client) throws MyException {
//        initializeConnection();
//        EmployeeDto empDto = new EmployeeDto(username, password);
//        Request request = new Request.Builder().type(RequestType.LOGIN).data(empDto).build();
//        sendRequest(request);
//
//        Response response = readResponse();
//        if(response.type() == ResponseType.OK){
//            this.client = client;
//            return;
//        }
//        if(response.type() == ResponseType.ERROR){
//            String err = response.data().toString();
//            closeConnection();
//            throw new MyException(err);
//        }
//    }
//
//    @Override
//    public void purchase(long gameId, int noOfTickets, String clientName) throws MyException {
//        TicketPurchaseDto ticketPurchase = new TicketPurchaseDto(gameId, noOfTickets, clientName);
//        Request request = new Request.Builder().type(RequestType.PURCHASE).data(ticketPurchase).build();
//        sendRequest(request);
//
//        Response response = readResponse();
//        if(response.type() == ResponseType.ERROR){
//            closeConnection();
//            String err = response.data().toString();
//            throw new MyException(err);
//        }
//    }
//
//    @Override
//    public List<Game> findAllGames() throws MyException{
//        Request request = new Request.Builder().type(RequestType.GET_GAMES).build();
//        sendRequest(request);
//
//        Response response = readResponse();
//        if(response.type() == ResponseType.ERROR){
////            closeConnection();
//            String err = response.data().toString();
//            throw new MyException(err);
//        }
//        List<Game> games = (List<Game>)response.data();
//        return games;
//    }
//
//    @Override
//    public void logOut(Employee employee, IObserver client) throws MyException{
//        EmployeeDto empDto = DtoUtils.getDTO(employee);
//        Request request = new Request.Builder().type(RequestType.LOGOUT).data(empDto).build();
//        sendRequest(request);
//
//        Response response = readResponse();
//        closeConnection();
//        if(response.type() == ResponseType.ERROR)
//            throw new MyException(response.data().toString());
//    }
//
//    private void sendRequest(Request request)throws MyException {
//        try {
//            output.writeObject(request);
//            output.flush();
//        } catch (IOException e) {
//            throw new MyException("Error sending object " + e);
//        }
//    }
//
//    private Response readResponse() throws MyException {
//        Response response = null;
//        try{
//            response = qresponses.take();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    private void handleUpdate(Response response){
//        if (response.type()== ResponseType.GAME) {
//            System.out.println("handle update in proxy");
//            Game game = (Game)response.data();
//            client.updateGame(game);
//        }
//    }
//
//    private void closeConnection() {
//        finished = true;
//        try {
//            input.close();
//            output.close();
//            connection.close();
//            client = null;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void initializeConnection() {
//        try {
//            connection = new Socket(host,port);
//            output = new ObjectOutputStream(connection.getOutputStream());
//            output.flush();
//            input = new ObjectInputStream(connection.getInputStream());
//            finished = false;
//            startReader();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isUpdate(Response response){
//        return response.type()== ResponseType.GAME;
//    }
//
//    private void startReader(){
//        Thread tw = new Thread(new ReaderThread());
//        tw.start();
//    }
//
//    private class ReaderThread implements Runnable{
//        public void run() {
//            while(!finished){
//                try {
//                    Object response = input.readObject();
//                    System.out.println("response received " + response);
//                    if (isUpdate((Response)response)){
//                        handleUpdate((Response)response);
//                    }else{
//
//                        try {
//                            qresponses.put((Response)response);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } catch (IOException | ClassNotFoundException e) {
//                    System.out.println("Reading error " + e);
//                }
//            }
//        }
//    }
}