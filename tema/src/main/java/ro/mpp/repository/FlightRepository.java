package ro.mpp.repository;

import ro.mpp.domain.Flight;
import ro.mpp.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends Repository<Integer, Flight> {
    List<Flight> findByDestinationDate(String destination, LocalDate date);

}
