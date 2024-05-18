package persistence.repository;

import app.model.Flight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import persistence.FlightRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class FlightDBRepository implements FlightRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public FlightDBRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }
    @Override
    public List<Flight> findByDestinationDate(String destination, LocalDate date) {
        logger.traceEntry();
        List<Flight> flights =  new ArrayList<>();
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preStmt=con.prepareStatement("select * from flights where destination LIKE ? and date LIKE ?")) {
            preStmt.setString(1, destination);
            preStmt.setString(2, String.valueOf(date));
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    LocalTime time = LocalTime.parse(result.getString("time"));
                    String airport = result.getString("airport");
                    int noSeats = result.getInt("no_seats");
                    Flight flight =  new Flight(destination, date, time, airport, noSeats);
                    flight.setId(id);
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {

            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(flights);
        return flights;
    }

    @Override
    public Flight findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Flight> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Flight> flights=new ArrayList<>();
        try (PreparedStatement preStmt=con.prepareStatement("select * from flights")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String destination = result.getString("destination");
                    LocalDate date = LocalDate.parse(result.getString("date"));
                    LocalTime time = LocalTime.parse(result.getString("time"));
                    String airport = result.getString("airport");
                    int noSeats = result.getInt("no_seats");
                    Flight flight = new Flight(destination, date, time, airport, noSeats);
                    flight.setId(id);
                    flights.add(flight);
                }
            }
        } catch (SQLException e) {

            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(flights);
        return flights;
    }

    @Override
    public void save(Flight entity) {

    }

    @Override
    public void delete(Integer integer) {
    }

    @Override
    public void update(Flight entity){
        logger.traceEntry( "saving task {}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update flights set destination=?, date=?, time=?, airport=?, no_seats=? where id=?")) {
            preStmt.setString(1,entity.getDestination());
            preStmt.setString(2, entity.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            preStmt.setString(3, entity.getHour().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
            preStmt.setString(4, entity.getAirport());
            preStmt.setInt(5, entity.getNoSeats());
            preStmt.setInt(6, entity.getId());
            int result=preStmt.executeUpdate();
            logger.trace("Update () instances", result);
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            logger.traceExit();
            ex.printStackTrace();
        }
    }
}
