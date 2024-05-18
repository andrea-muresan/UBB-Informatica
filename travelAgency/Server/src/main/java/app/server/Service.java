package app.server;


import app.model.Flight;
import app.model.Ticket;
import app.model.User;
//import persistence.FlightRepository;
import persistence.FlightRepository;
import persistence.TicketRepository;
import persistence.UserRepository;
import services.IObserver;
import services.MyException;
import app.utils.PassEncTech1;
import services.IServices;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IServices {
    private UserRepository userRepo;
    private FlightRepository flightRepo;
    private TicketRepository ticketRepo;
    private final PassEncTech1 encTech1;

    private Map<Integer, IObserver> loggedUsers;
    private final int defaultThreadsNo = 5;

    public Service(UserRepository userRepo, FlightRepository flightRepo, TicketRepository ticketRepo) {
        this.userRepo = userRepo;
        this.flightRepo = flightRepo;
        this.ticketRepo = ticketRepo;
        encTech1 = new PassEncTech1();
        loggedUsers = new ConcurrentHashMap<>();

    }

    @Override
    public void findUserByCredentials(String username, String password, IObserver userObs) throws MyException {
        User user = userRepo.findByUsername(username);
        if (!loggedUsers.containsKey(user.getId()))
        {
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    loggedUsers.put(user.getId(), userObs);
                } else {
                    throw new MyException("Wrong password");
                }
            } else {
                throw new MyException("Authentication failed.");
            }
        } else {
            throw new MyException("Already connected");
        }

    }

    @Override
    public boolean signUp(String username, String password) {
        if (!Objects.equals(username, "") && !Objects.equals(password, "")) {
            userRepo.save(new User(username, encTech1.encrypt(password)));
            return true;
        }
        return false;
    }

    @Override
    public Iterable<Flight> getAllFlights() {
        return flightRepo.findAll();
    }

    @Override
    public Iterable<Flight> getFlightsByDestinationDate(String dest, LocalDate date) {
        return flightRepo.findByDestinationDate(dest, date);
    }

    @Override
    public synchronized void buyTicket(Flight flight, String client, String address, String tourists, String noSeats) throws MyException{
        int noSeatsInt = Integer.parseInt(noSeats);


        if (!tourists.isBlank() && tourists.split(";").length >= noSeatsInt) {
            throw new MyException("Too many passengers for " + noSeatsInt + " seats");

        }
        if (noSeatsInt > flight.getNoSeats()) {
            throw new MyException("There are not enough seats");
        }

        if (noSeatsInt < 1) {
            throw new MyException("Enter at list one seat");
        }

        try {
            ticketRepo.save(new Ticket(flight.getId(), client, address, tourists, noSeatsInt));
            flight.setNoSeats(flight.getNoSeats() - noSeatsInt);
            flightRepo.update(flight);
            notifyUsers(flight);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void logOut(User user, IObserver userObs) throws MyException {
        User usr = userRepo.findByUsername(user.getUsername());
        if (usr == null)
            throw new MyException("User " + user.getUsername() + "is not logged in!");
        IObserver user1 = loggedUsers.remove(usr.getId());
    }

    private void notifyUsers(Flight flight) {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
        for (IObserver user : loggedUsers.values()) {
            executor.execute(() -> {
                System.out.println("Flight update in Service" + flight + " " + user);
                try {
                    user.updateFlight(flight);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
