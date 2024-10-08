package network.protobuffprotocol;

import app.model.Flight;
import app.model.User;
import app.model.dto.TicketPurchaseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProtoUtils {
    public static AppProtobuffs.Request createLoginRequest (String username, String password) {
        AppProtobuffs.User user = AppProtobuffs.User.newBuilder().setUsername(username).setPassword(password).build();
        AppProtobuffs.Request request = AppProtobuffs.Request.newBuilder().setType(AppProtobuffs.Request.Type.LOGIN)
                .setUser(user).build();
        return request;
    }

    public static AppProtobuffs.Request createLogoutRequest(User employee){
        AppProtobuffs.User user = AppProtobuffs.User.newBuilder().setUsername(employee.getUsername()).build();
        AppProtobuffs.Request request = AppProtobuffs.Request.newBuilder().setType(AppProtobuffs.Request.Type.LOGOUT)
                .setUser(user).build();
        return request;
    }

    public static AppProtobuffs.Response createOkResponse(){
        AppProtobuffs.Response response = AppProtobuffs.Response.newBuilder()
                .setType(AppProtobuffs.Response.Type.OK).build();
        return response;
    }

    public static AppProtobuffs.Response createErrorResponse(String text){
        AppProtobuffs.Response response = AppProtobuffs.Response.newBuilder()
                .setType(AppProtobuffs.Response.Type.ERROR)
                .setError(text).build();
        return response;
    }

    public static String getError(AppProtobuffs.Response response){
        String errorMessage = response.getError();
        return errorMessage;
    }

    public static Flight getFlight(AppProtobuffs.Response response) {
        Flight fl = new Flight( response.getFlight().getDestination(), parseStringToLocalDate(response.getFlight().getDate()), LocalTime.parse(response.getFlight().getTime()), response.getFlight().getAirport(), response.getFlight().getNoSeats());
        fl.setId(response.getFlight().getId());
        return fl;
    }

    public static AppProtobuffs.Request createPurchaseRequest(Flight flight, String name, String address, String tourists, String noSeats) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = flight.getDate().format(formatter);

        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = flight.getHour().format(formatterTime);

        AppProtobuffs.Flight fl = AppProtobuffs.Flight.newBuilder().setId(flight.getId()).setDestination(flight.getDestination()).setDate(formattedDate)
                .setTime(formattedTime).setNoSeats(flight.getNoSeats()).build();
        AppProtobuffs.TicketPurchase tpDto = AppProtobuffs.TicketPurchase.newBuilder().setFlight(fl).setClientName(name).setClientAddress(address)
                .setTouristsNames(tourists).setNoOfTickets(noSeats).build();
        AppProtobuffs.Request request = AppProtobuffs.Request.newBuilder().setType(AppProtobuffs.Request.Type.PURCHASE).setTicket(tpDto).build();
        return request;
    }

    public static AppProtobuffs.Request createFindAllFlightsRequest() {
        AppProtobuffs.Request request = AppProtobuffs.Request.newBuilder().setType(AppProtobuffs.Request.Type.GET_FLIGHTS)
                .build();
        return request;
    }

    public static Iterable<Flight> getAllFlights(AppProtobuffs.Response response) {
        List<Flight> flights = new ArrayList<>();
        for(int i = 0; i < response.getFlightsCount(); i++){
            AppProtobuffs.Flight fl = response.getFlights(i);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate localDate = LocalDate.parse(fl.getDate(), formatter);

            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(fl.getTime(), formatterTime);

            Flight flight = new Flight(fl.getDestination(), localDate, localTime, fl.getAirport(), fl.getNoSeats());
            flight.setId(fl.getId());
            flights.add(flight);
        }
        return flights;
    }

    public static AppProtobuffs.Request createFindFlightsDestDateRequest(String dest, String date) {
        AppProtobuffs.SearchFlights srcFl = AppProtobuffs.SearchFlights.newBuilder().setDestination(dest).setDate(date).build();
        AppProtobuffs.Request request = AppProtobuffs.Request.newBuilder().setType(AppProtobuffs.Request.Type.GET_FLIGHTS_DEST_DATE).setSearchFlights(srcFl)
                .build();
        return request;
    }


    private static LocalDate parseStringToLocalDate(String str){
        String[] parts = str.split("-");
        return LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
    }
}