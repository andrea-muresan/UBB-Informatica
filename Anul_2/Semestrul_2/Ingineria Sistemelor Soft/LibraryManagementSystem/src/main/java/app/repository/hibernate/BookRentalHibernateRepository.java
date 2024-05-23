package app.repository.hibernate;

import app.domain.BookRental;
import app.repository.IBookRentalRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

public class BookRentalHibernateRepository implements IBookRentalRepository {
    @Override
    public BookRental findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from BookRental where id=:id ", BookRental.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public BookRental save(BookRental entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        // nu actualizeaza id-ul
        return entity;
    }

    @Override
    public BookRental update(BookRental entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(BookRental.class, entity.getId()))) {
                System.out.println("In update, am gasit mesajul cu id-ul "+entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
        return null;
    }

    @Override
    public BookRental delete(Integer id) {
        return null;
    }

    @Override
    public List<BookRental> getAll() {
        return null;
    }




    @Override
    public List<BookRental> getAllNotReturned() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from BookRental where returned=0", BookRental.class)
                    .getResultList();
        }
    }

    @Override
    public BookRental findByUserAndBook(Integer userId, Integer bookId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from BookRental where bookId=:idB and userId=:idU", BookRental.class)
                    .setParameter("idB", bookId)
                    .setParameter("idU", userId)
                    .getSingleResultOrNull();
        }
    }
}
