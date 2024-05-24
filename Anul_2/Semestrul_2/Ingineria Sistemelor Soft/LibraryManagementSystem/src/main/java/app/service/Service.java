package app.service;

import app.domain.*;
import app.domain.dto.BorrowDto;
import app.repository.*;
import app.service.utils.Observable;
import app.service.utils.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service implements Observable {
    private IBookRepository bookRepo;
    private IBookSetRepository bookSetRepo;

    private IBookBorrowRepository bookBorrowRepo;
    private ILibrarianRepository librarianRepo;
    private IClientRepository clientRepo;

    private List<Observer> observers = new ArrayList<>();


    public Service(IBookRepository bookRepo, IBookSetRepository bookSetRepo, IBookBorrowRepository bookBorrowRepo, ILibrarianRepository librarianRepo, IClientRepository clientRepo) {
        this.bookRepo = bookRepo;
        this.bookSetRepo = bookSetRepo;
        this.bookBorrowRepo = bookBorrowRepo;
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

    public List<BorrowDto> findAllLandings() {
        List<BorrowDto> borrows = new ArrayList<>();
        for (BookBorrow bkBr: bookBorrowRepo.getAllNotReturned()) {
            // Find the book
            Book bk = bookRepo.findOne(bkBr.getBookId());
            Client cl = clientRepo.findOne(bkBr.getUserId());
            borrows.add(new BorrowDto(cl, bk, bkBr.getDateStart(), bkBr.getDateEnd()));
        }

        return borrows;
    }

    public List<BorrowDto> findAllBorrowsClient(Client client){
        List<BorrowDto> borrows = new ArrayList<>();
        for (BookBorrow bkBr: bookBorrowRepo.getAllClient(client.getId())) {
            // Find the book
            Book bk = bookRepo.findOne(bkBr.getBookId());
            borrows.add(new BorrowDto(client, bk, bkBr.getDateStart(), bkBr.getDateEnd()));
        }

        return borrows;
    }

    public void borrowBook(int user_id, BookSet bookSet) throws Exception {
        if (bookSet.getNoCopiesAvailable() == 0)
            throw new Exception("Carte indisponibila!");
        else {
            // Find an available book
            Book book = bookRepo.findBookAvailableISBN(bookSet.getISBN());
            // Add book borrow
            bookBorrowRepo.save(new BookBorrow(user_id, book.getId(), LocalDate.now().toString(), LocalDate.now().plusDays(14).toString(), 0));
            // set the book as unavailable
            book.setAvailable(0);
            bookRepo.update(book);
            notifyUsers();
        }
    }

    public void addBook(String name, String author, String genre, String ISBN, String language) throws Exception {
        if (name.isEmpty() || author.isEmpty() || genre.isEmpty() || ISBN.isEmpty())
            throw new Exception("Nu sunt permise campurile goale!");
        else {
            bookRepo.save(new Book(name, author, ISBN, genre, language, 1));
            notifyUsers();
        }
    }

    public void deleteBook(String id){
        Integer idNr = Integer.parseInt(id);

        bookBorrowRepo.delete(idNr);
        bookRepo.delete(idNr);
        notifyUsers();
    }

    public void returnBook(String bookId) {
        Integer bkId = Integer.parseInt(bookId);
        BookBorrow landing = bookBorrowRepo.findByBook(bkId);
        // Mark the book as returned
        landing.setReturned(1);
        bookBorrowRepo.update(landing);
        //Find the book that was borrowed & set it as available
        Book book = bookRepo.findOne(landing.getBookId());
        book.setAvailable(1);
        bookRepo.update(book);
        notifyUsers();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyUsers() {
        observers.forEach(Observer::update);
    }
}
