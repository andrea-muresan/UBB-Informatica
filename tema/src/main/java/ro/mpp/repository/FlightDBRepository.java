package ro.mpp.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.domain.Flight;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class FlightDBRepository implements FlightRepository{
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
        try (PreparedStatement preStmt=con.prepareStatement("select * from flights where destination=? and STRFTIME('%Y-%m-%d', time) = ?")) {
            preStmt.setString(1, destination);
            preStmt.setString(2, String.valueOf(date));
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    LocalDateTime time = result.getTimestamp("time").toLocalDateTime();
                    String airport = result.getString("airport");
                    int noSeats = result.getInt("no_seats");
                    Flight flight =  new Flight(destination, date, time.toLocalTime(), airport, noSeats);
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
    public Optional<Flight> findOne(Integer integer) {
        return Optional.empty();
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
                    LocalDateTime time = result.getTimestamp("time").toLocalDateTime();
                    String airport = result.getString("airport");
                    int noSeats = result.getInt("no_seats");
                    Flight flight = new Flight(destination, time.toLocalDate(), time.toLocalTime(), airport, noSeats);
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
    public void update(Flight entity) {
        logger.traceEntry( "saving task {}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update flights set destination=?, time=?, airport=?, no_seats=?")) {
            preStmt.setString(1,entity.getDestination());
            LocalDateTime dateTime = LocalDateTime.of(entity.getDate(), entity.getHour());
            preStmt.setTimestamp(2, Timestamp.valueOf(dateTime));
            preStmt.setString(3, entity.getAirport());
            preStmt.setInt(4, entity.getNoSeats());
            int result=preStmt.executeUpdate();
            logger.trace("Update () instances", result);
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            logger.traceExit();
        }
    }
}
