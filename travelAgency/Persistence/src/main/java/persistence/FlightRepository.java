package persistence;


import app.model.Flight;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends Repository<Integer, Flight> {
    List<Flight> findByDestinationDate(String destination, LocalDate date);

}
