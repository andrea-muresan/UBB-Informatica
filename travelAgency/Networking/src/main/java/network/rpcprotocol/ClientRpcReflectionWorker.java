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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.SQLException;
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
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    @Override
    public void updateFlight(Flight flight) throws IOException {
        // FlightDto mdto= DTOUtils.getDTO(message);
        Response response = new Response.Builder().type(ResponseType.FLIGHT).data(flight).build();
        System.out.println("notify users about flight " + flight);
        sendResponse(response);
    }


    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    private Response handleRequest(Request request) {
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this, request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        UserDto udto=(UserDto)request.data();
        User user=DtoUtils.getFromDTO(udto);
        try {
            server.findUserByCredentials(user.getUsername(), user.getPassword(), this);
            return okResponse;
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_FLIGHTS_DEST_DATE(Request request){
        System.out.println("Get flights in handleGET_FLIGHTS_DEST_DATE " + request.type());
        try {
            SearchFlightsDto fldto = (SearchFlightsDto)request.data();
            Iterable<Flight> flightList = server.getFlightsByDestinationDate(fldto.getDestination(), fldto.getDate());
            return new Response.Builder().type(ResponseType.GET_FLIGHTS_DEST_DATE).data(flightList).build();
        } catch (MyException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        UserDto userDto = (UserDto)request.data();
        User user = DtoUtils.getFromDTO(userDto);
        try {
            server.logOut(user, this);
            connected = false;
            return okResponse;
        } catch (MyException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_FLIGHTS(Request request){
        System.out.println("Get flights in handleGET_FLIGHTS " + request.type());
        try {
            Iterable<Flight> flightList = server.getAllFlights();
            return new Response.Builder().type(ResponseType.GET_FLIGHTS).data(flightList).build();
        } catch (MyException e) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handlePURCHASE(Request request) {
        System.out.println("handle Purchase ..." + request.type());
        TicketPurchaseDto tpDto = (TicketPurchaseDto) request.data();
        try {
            server.buyTicket(tpDto.getFlight(), tpDto.getClientName(), tpDto.getClientAddress(), tpDto.getTouristsNames(), tpDto.getNoOfTickets());
            return new Response.Builder().type(ResponseType.OK).data(tpDto.getFlight()).build();
        } catch (MyException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}