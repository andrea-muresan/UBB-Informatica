package persistence.hibernate.flight;


import app.model.Flight;
import app.model.Ticket;
import org.hibernate.Session;
import persistence.FlightRepository;
import persistence.TicketRepository;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TicketHibernateRepository implements TicketRepository {


    @Override
    public Ticket findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        return null;
    }

    @Override
    public void save(Ticket entity) throws SQLException {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Ticket entity) throws SQLException {

    }
}
