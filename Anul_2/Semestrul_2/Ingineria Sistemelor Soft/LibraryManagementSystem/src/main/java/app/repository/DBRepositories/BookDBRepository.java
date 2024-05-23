package app.repository.DBRepositories;

import app.domain.Book;
import app.domain.Client;
import app.repository.IBookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDBRepository implements IBookRepository {

    public BookDBRepository() {
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            try (PreparedStatement preStmt = con.prepareStatement("select *, count(ISBN) AS noC from book group by ISBN")) {
                try (ResultSet result = preStmt.executeQuery()) {
                    while (result.next()) {
                        Integer id = result.getInt("id");
                        String name = result.getString("name");
                        String author = result.getString("author");
                        String ISBN = result.getString("ISBN");
                        String genre = result.getString("genre");
                        String language = result.getString("language");
                        Integer noCopies = result.getInt("noC");

                        Book book = new Book(name, author, ISBN, genre, language);
                        book.setId(id);
                        book.setNoCopies(noCopies);
                        books.add(book);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
