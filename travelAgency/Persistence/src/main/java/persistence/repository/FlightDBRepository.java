package persistence.repository;

import app.model.Flight;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import persistence.FlightRepository;

import java.io.IOException;
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


@Component
public class FlightDBRepository implements FlightRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public FlightDBRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    public FlightDBRepository() {
        Properties props = new Properties();
        try {
            props.load(FlightDBRepository.class.getResourceAsStream("/bd.properties"));
            System.out.println("Properties set. ");
            props.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find properties " + e);
            dbUtils = null;
            return;
        }
        dbUtils = new JdbcUtils(props);
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
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        Flight flight = null;
        try(PreparedStatement preStm = con.prepareStatement("""
                SELECT * FROM flights
                WHERE id = ?""")){
            preStm.setInt(1, integer);
            try(ResultSet result = preStm.executeQuery()){
                if(result.next()){
                    Integer id = result.getInt("id");
                    String destination = result.getString("destination");
                    LocalDate date = LocalDate.parse(result.getString("date"));
                    LocalTime time = LocalTime.parse(result.getString("time"));
                    String airport = result.getString("airport");
                    int noSeats = result.getInt("no_seats");
                    flight = new Flight(destination, date, time, airport, noSeats);
                    flight.setId(id);
                }
            }
        }
        catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return flight;
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
    public Flight save(Flight entity) {
        logger.traceEntry("saving {} ", entity);
        Connection con = dbUtils.getConnection();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try(PreparedStatement preStm = con.prepareStatement("INSERT INTO flights (destination, date, time, " +
                "airport, no_seats) VALUES (?, ?, ?, ?, ?);")){
            preStm.setString(1, entity.getDestination());
            preStm.setString(2, entity.getDate().toString());
            preStm.setString(3, entity.getHour().format(formatter));
            preStm.setString(4, entity.getAirport());
            preStm.setInt(5, entity.getNoSeats());
            int result = preStm.executeUpdate();

            if (result == 1) {
                int id = getMaxId();
                if (id != -1)
                    entity.setId(id);
            }

            logger.trace("Saved {} instances", result);
        }
        catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
        return entity;
    }

    public int getMaxId() {
        logger.traceEntry("getting maximum id");
        Connection con = dbUtils.getConnection();

        String sql = "SELECT MAX(id) FROM flights";
        try (PreparedStatement preStmt = con.prepareStatement(sql)) {
            try (ResultSet rs = preStmt.executeQuery()) {
                if (rs.next()) {
                    int maxId = rs.getInt(1);
                    logger.traceExit("maxId: {}", maxId);
                    return maxId;
                }
            }
        } catch (SQLException ex) {
            logger.error("Database error: ", ex);
            System.err.println("Error DB: " + ex);
            logger.traceExit();
            ex.printStackTrace();
        }
        return -1; // Return a default value or throw an exception as per your requirement
    }


    @Override
    public void delete(Integer id) {
        logger.traceEntry("deleting task {} ", id);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStm = con.prepareStatement("DELETE FROM flights WHERE id = ?;")){
            preStm.setInt(1, Math.toIntExact(id));
            int result = preStm.executeUpdate();
            logger.trace("Deleted {} instances", result);
        }
        catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        logger.traceExit();
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
