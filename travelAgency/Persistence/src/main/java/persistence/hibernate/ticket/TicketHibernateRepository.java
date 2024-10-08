package persistence.hibernate.ticket;


import app.model.Ticket;
import org.hibernate.Session;
import persistence.TicketRepository;


import java.sql.SQLException;

public class TicketHibernateRepository implements TicketRepository {


    @Override
    public Ticket findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Ticket ", Ticket.class).getResultList();
        }
    }

    @Override
    public Ticket save(Ticket entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Ticket entity)  {

    }
}
