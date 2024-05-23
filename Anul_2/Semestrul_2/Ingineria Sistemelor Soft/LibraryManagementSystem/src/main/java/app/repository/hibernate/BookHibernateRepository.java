package app.repository.hibernate;

import app.domain.Book;
import app.repository.IBookRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookHibernateRepository implements IBookRepository {

    public BookHibernateRepository(){}

    @Override
    public Book findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Book where id=:id ", Book.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public Book save(Book entity) {
        return null;
    }

    @Override
    public Book update(Book entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Book.class, entity.getId()))) {
                System.out.println("In update, am gasit cartea cu id-ul "+ entity.getId());
                session.merge(entity);
                session.flush();
            }
        });

        return entity;
    }

    @Override
    public Book delete(Integer id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();

        try (Session session= HibernateUtils.getSessionFactory().openSession()) {
            String hql = "SELECT b.id, b.name, b.author, b.ISBN, b.genre, b.language, b.available " +
                    "FROM Book b";

            Query query = session.createQuery(hql, Object[].class);
            List<Object[]> results = query.getResultList();

            for (Object[] row : results) {
                Book book = getBook(row);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    private static Book getBook(Object[] row) {
        Integer id = (Integer) row[0];
        String name = (String) row[1];
        String author = (String) row[2];
        String ISBN = (String) row[3];
        String genre = (String) row[4];
        String language = (String) row[5];
        Integer available = (Integer) row[6];

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setISBN(ISBN);
        book.setGenre(genre);
        book.setLanguage(language);
        book.setAvailable(available);
        return book;
    }
}
