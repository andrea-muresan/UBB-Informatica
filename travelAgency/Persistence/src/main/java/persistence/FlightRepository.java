package persistence;

import app.model.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends Repository<Integer, Flight> {
    List<Flight> findByDestinationDate(String destination, LocalDate date);

}
