package app.repository.hibernate;

import app.domain.Book;
import app.domain.BookBorrow;
import app.repository.IBookBorrowRepository;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;

public class BookBorrowHibernateRepository implements IBookBorrowRepository {
    @Override
    public BookBorrow findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from BookBorrow where id=:id ", BookBorrow.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public BookBorrow save(BookBorrow entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        // nu actualizeaza id-ul
        return entity;
    }

    @Override
    public BookBorrow update(BookBorrow entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(BookBorrow.class, entity.getId()))) {
                System.out.println("In update, am gasit cartea cu id-ul "+entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
        return null;
    }

    @Override
    public BookBorrow delete(Integer id) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            BookBorrow book=session.createQuery("from BookBorrow where bookId=?1",BookBorrow.class).
                    setParameter(1,id).uniqueResult();
            System.out.println("In delete am gasit imprumuturile " + book);
            if (book!=null) {
                session.remove(book);
                session.flush();
            }
        });
        return null;
    }

    @Override
    public List<BookBorrow> getAll() {
        return null;
    }

    @Override
    public List<BookBorrow> getAllNotReturned() {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from BookBorrow where returned=0", BookBorrow.class)
                    .getResultList();
        }
    }

    @Override
    public BookBorrow findByBook(Integer bookId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from BookBorrow where bookId=:idB", BookBorrow.class)
                    .setParameter("idB", bookId)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public List<BookBorrow> getAllClient(Integer clientId) {
        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from BookBorrow where returned=0 and userId=:id", BookBorrow.class)
                    .setParameter("id", clientId)
                    .getResultList();
        }
    }
}
