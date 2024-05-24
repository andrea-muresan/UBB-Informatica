package app.repository.hibernate;

import app.domain.BookSet;
import app.repository.IBookSetRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class BookSetHibernateRepository implements IBookSetRepository {

    public BookSetHibernateRepository() {
    }

    @Override
    public BookSet findOne(Integer id) {
        return null;
    }

    @Override
    public BookSet save(BookSet entity) {
        return null;
    }

    @Override
    public BookSet update(BookSet entity) {
        return null;
    }

    @Override
    public BookSet delete(Integer id) {
        return null;
    }

    @Override
    public List<BookSet> getAll() {
        List<BookSet> books = new ArrayList<>();

        try (Session session= HibernateUtils.getSessionFactory().openSession()) {
            String hql = "SELECT b.id, b.name, b.author, b.ISBN, b.genre, b.language " +
                    "FROM BookSet b GROUP BY b.ISBN";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            List<Object[]> results = query.getResultList();

            for (Object[] row : results) {
                BookSet book = getBook(row);
                BookSetDetails(book);

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    private void BookSetDetails(BookSet bookSet){
        try (Session session= HibernateUtils.getSessionFactory().openSession()) {
            String hql = "SELECT b.id, b.available " +
                    "FROM Book b WHERE b.ISBN=:isbn";

            Query<Object[]> query = session.createQuery(hql, Object[].class)
                    .setParameter("isbn", bookSet.getISBN());

            List<Object[]> results = query.getResultList();

            for (Object[] row : results) {
                Integer id = (Integer) row[0];
                Integer av = (Integer) row[1];

                if (av == 0) bookSet.MoveBookToUnavailable(id);
                else bookSet.MoveBookToAvailable(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BookSet getBook(Object[] row) {
        Integer id = (Integer) row[0];
        String name = (String) row[1];
        String author = (String) row[2];
        String ISBN = (String) row[3];
        String genre = (String) row[4];
        String language = (String) row[5];

        BookSet book = new BookSet();
        book.setName(name);
        book.setAuthor(author);
        book.setISBN(ISBN);
        book.setGenre(genre);
        book.setLanguage(language);
        book.setId(id);
        return book;
    }

    @Override
    public void MoveBookToUnavailable() {

    }

    @Override
    public void moveBookToAvailable() {

    }
}
