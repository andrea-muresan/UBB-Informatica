package app.service;

import app.domain.*;
import app.repository.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Service {
    private IBookRepository bookRepo;
    private IBookSetRepository bookSetRepo;

    private IBookRentalRepository bookRentalRepo;
    private ILibrarianRepository librarianRepo;
    private IClientRepository clientRepo;

    public Service(IBookRepository bookRepo, IBookSetRepository bookSetRepo, IBookRentalRepository bookRentalRepo, ILibrarianRepository librarianRepo, IClientRepository clientRepo) {
        this.bookRepo = bookRepo;
        this.bookSetRepo = bookSetRepo;
        this.bookRentalRepo = bookRentalRepo;
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
    public List<BookSet> findAllBooks()  {
        return bookSetRepo.getAll();
    }

    public void rentBook(int user_id, int book_id) {
        // Add book rental
        bookRentalRepo.save(new BookRental(user_id, book_id, LocalDate.now().toString(), LocalDate.now().plusDays(14).toString(), 0));
        //Find the book that was rented & set it as unavailable
        Book book = bookRepo.findOne(book_id);
        book.setAvailable(0);
        bookRepo.update(book);
    }

    public void returnBook(BookRental bkRental) {
        // Mark the book as returned
        bkRental.setReturned(1);
        bookRentalRepo.update(bkRental);
        //Find the book that was rented & set it as available
        Book book = bookRepo.findOne(bkRental.getBookId());
        book.setAvailable(1);
        bookRepo.update(book);
    }
}
