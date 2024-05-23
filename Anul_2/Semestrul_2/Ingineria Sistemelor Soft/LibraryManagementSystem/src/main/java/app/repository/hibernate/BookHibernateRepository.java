package app.repository.hibernate;

import app.domain.Book;
import app.repository.IBookRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;

public class BookHibernateRepository implements IBookRepository {

    public BookHibernateRepository(){}

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();

        try (Session session= HibernateUtils.getSessionFactory().openSession()) {
            String hql = "SELECT b.id, b.name, b.author, b.ISBN, b.genre, b.language, COUNT(b.ISBN) " +
                    "FROM Book b GROUP BY b.ISBN";

            Query query = session.createQuery(hql, Object[].class);
            List<Object[]> results = query.getResultList();

            for (Object[] row : results) {
                Integer id = (Integer) row[0];
                String name = (String) row[1];
                String author = (String) row[2];
                String ISBN = (String) row[3];
                String genre = (String) row[4];
                String language = (String) row[5];
                Integer noCopies = ((Long) row[6]).intValue(); // COUNT returns a Long

                Book book = new Book();
                book.setId(id);
                book.setName(name);
                book.setAuthor(author);
                book.setISBN(ISBN);
                book.setGenre(genre);
                book.setLanguage(language);
                book.setNoCopies(noCopies);

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
}
