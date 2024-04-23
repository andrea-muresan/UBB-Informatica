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
    public Flight findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Flight> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
           Iterable<Flight> flights = session.createQuery("from Flight ", Flight.class).getResultList();

           return null;
        }
    }

    @Override
    public void save(Flight entity) throws SQLException {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Flight entity) throws SQLException {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Flight.class, entity.getId()))) {
                System.out.println("In update, am gasit mesajul cu id-ul "+entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
    }

    @Override
    public List<Flight> findByDestinationDate(String destination, LocalDate date) {
        try(Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Flight flight where flight.destination=?1 and flight.date=?2",Flight.class).setParameter(1,destination).setParameter(2, date).getResultList();
        }
    }
}
