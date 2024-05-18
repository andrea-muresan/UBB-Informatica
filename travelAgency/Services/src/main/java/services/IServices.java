package services;


import app.model.Flight;
import app.model.User;

import java.sql.SQLException;
import java.time.LocalDate;

public interface IServices {

    void findUserByCredentials(String username, String password, IObserver userObs) throws MyException;

    boolean signUp(String username, String password);

    Iterable<Flight> getAllFlights() throws MyException;

    Iterable<Flight> getFlightsByDestinationDate(String dest, LocalDate date) throws MyException;

    void buyTicket(Flight flight, String client, String address, String tourists, String noSeats) throws MyException, SQLException;

    void logOut(User user, IObserver userObs) throws MyException;
}
