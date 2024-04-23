package persistence.hibernate.flight;

import app.model.Flight;
import org.hibernate.Session;
import persistence.FlightRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class FlightHibernateRepository implements FlightRepository {


    @Override
    public List<Flight> findByDestinationDate(String destination, LocalDate date) {
        return null;
    }

    @Override
    public Flight findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Flight> findAll() {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Flight entity) throws SQLException {

    }

    @Override
    public void save(Flight entity) throws SQLException {

    }
}
