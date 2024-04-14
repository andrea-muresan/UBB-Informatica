package app.services;

import app.model.Flight;

import java.time.LocalDate;

public interface IServices {
    boolean findUserByCredentials(String username, String password);
    boolean signUp(String username, String password);

    Iterable<Flight> getAllFlights();
    Iterable<Flight> getFlightsByDestinationDate(String dest, LocalDate date);
    String buyTicket(Flight flight, String client, String address, String tourists, String noSeats);
}
