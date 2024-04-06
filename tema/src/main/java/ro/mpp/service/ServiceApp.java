package ro.mpp.service;

import ro.mpp.domain.Flight;
import ro.mpp.domain.Ticket;
import ro.mpp.repository.FlightDBRepository;
import ro.mpp.repository.FlightRepository;
import ro.mpp.repository.TicketDBRepository;
import ro.mpp.repository.TicketRepository;

import java.time.LocalDate;
import java.util.Arrays;

public class ServiceApp {
    private FlightRepository flightRepo;
    private TicketRepository ticketRepo;

    public ServiceApp(FlightRepository flightRepo, TicketRepository ticketRepo) {
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
    }

    public Iterable<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    public Iterable<Flight> getFlightsByDestinationDate(String dest, LocalDate date) {
        return flightRepo.findByDestinationDate(dest, date);
    }

    public String buyTicket(Flight flight, String client, String address, String tourists, String noSeats) {
        try {
            Integer noSeatsInt = Integer.parseInt(noSeats);


            if (!tourists.isBlank() && tourists.split(";").length >= noSeatsInt) {
                return "Too many passengers for " + noSeatsInt + " seats";

            }
            if (noSeatsInt > flight.getNoSeats()) {
                return "There are not enough seats";
            }

            ticketRepo.save(new Ticket(flight, client, address, tourists, noSeatsInt));
            flight.setNoSeats(flight.getNoSeats() - noSeatsInt);
            flightRepo.update(flight);
            return "Success";
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
