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
    public Book findOne(Integer id) {
        return null;
    }

    @Override
    public Book save(Book entity) {
        return null;
    }

    @Override
    public Book update(Book entity) {
        return null;
    }

    @Override
    public Book delete(Integer id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            try (PreparedStatement preStmt = con.prepareStatement("select * from book")) {
                try (ResultSet result = preStmt.executeQuery()) {
                    while (result.next()) {
                        Integer id = result.getInt("id");
                        String name = result.getString("name");
                        String author = result.getString("author");
                        String ISBN = result.getString("ISBN");
                        String genre = result.getString("genre");
                        String language = result.getString("language");
                        Integer available = result.getInt("available");

                        Book book = new Book(name, author, ISBN, genre, language, available);
                        book.setId(id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
