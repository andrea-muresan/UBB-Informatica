package app.network.rpcprotocol;

import app.model.Flight;
import app.services.IObserver;
import app.services.IServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientRpcReflectionWorker implements Runnable, IObserver {

    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private static final Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    public ClientRpcReflectionWorker(IServices server, Socket connection){
        this.server = server;
        this.connection = connection;

        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    @Override
    public void updateFlight(Flight flight) {

    }

//    @Override
//    public void updateGame(Game game) {
//        Response response = new Response.Builder().type(ResponseType.GAME).data(game).build();
//        System.out.println("notify employees in worker" + game);
//        sendResponse(response);
//    }
//
//    @Override
//    public void run() {
//        while (connected) {
//            try {
//                Object request = input.readObject();
//                Response response = handleRequest((Request) request);
//                if (response!=null){
//                    sendResponse(response);
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            input.close();
//            output.close();
//            connection.close();
//        } catch (IOException e) {
//            System.out.println("Error "+e);
//        }
//    }
//
//    private void sendResponse(Response response) {
//        try {
//            System.out.println("sending response " + response);
//            output.writeObject(response);
//            output.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Response handleRequest(Request request) {
//        Response response = null;
//        String handlerName = "handle" + (request).type();
//        System.out.println("handler name: " + handlerName);
//        try {
//            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
//            response = (Response)method.invoke(this, request);
//            System.out.println("Method " + handlerName + " invoked");
//        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    private Response handleLOGIN(Request request){
//        System.out.println("Login request ..."+request.type());
//        EmployeeDto empDto = (EmployeeDto) request.data();
//        try {
//            server.logIn(empDto.getUsername(), empDto.getPassword(), this);
//            return okResponse;
//        } catch (MyException e) {
//            connected = false;
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
//
//    private Response handleLOGOUT(Request request){
//        System.out.println("Logout request...");
//        EmployeeDto empDto = (EmployeeDto)request.data();
//        Employee employee = DtoUtils.getFromDTO(empDto);
//        try {
//            server.logOut(employee, this);
//            connected = false;
//            return okResponse;
//        } catch (MyException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
//
//    private Response handleGET_GAMES(Request request){
//        System.out.println("Get games in handleGET_GAMES " + request.type());
//        try {
//            List<Game> gamesList = server.findAllGames();
//            return new Response.Builder().type(ResponseType.GET_GAMES).data(gamesList).build();
//        } catch (MyException e) {
//            connected = false;
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
//
//    private Response handlePURCHASE(Request request) {
//        System.out.println("handle Purchase ..." + request.type());
//        TicketPurchaseDto tpDto = (TicketPurchaseDto) request.data();
//        try {
//            server.purchase(tpDto.getGameId(), tpDto.getNoOfTickets(), tpDto.getClientName());
//            return okResponse;
//        } catch (MyException e) {
//            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
//        }
//    }
}