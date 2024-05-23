package app.service;

import app.domain.Book;
import app.domain.Client;
import app.domain.Librarian;
import app.domain.User;
import app.repository.IBookRepository;
import app.repository.IClientRepository;
import app.repository.ILibrarianRepository;

import java.sql.SQLException;
import java.util.List;

public class Service {
    private IBookRepository bookRepo;
    private ILibrarianRepository librarianRepo;
    private IClientRepository clientRepo;

    public Service(IBookRepository bookRepo, ILibrarianRepository librarianRepo, IClientRepository clientRepo) {
        this.bookRepo = bookRepo;
        this.librarianRepo = librarianRepo;
        this.clientRepo = clientRepo;
    }

    public User findByCredentials(String u, String p) throws Exception {
        Client cl = clientRepo.findByCredentials(u, p);

        if (cl != null) return cl;
        else {
            Librarian lb = librarianRepo.findByCredentials(u, p);

            if (lb != null) return lb;
            else throw new Exception("User not found");
        }
    }
    public List<Book> findAllBooks() throws SQLException {
        return bookRepo.getAll();
    }
}
