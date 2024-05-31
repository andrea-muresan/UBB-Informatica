package app.domain.dto;

import app.domain.Book;
import app.domain.Client;
import app.domain.Entity;

public class BorrowDto extends Entity {
    private Client client;
    private Book book;
    private String dateStart;
    private String dateEnd;

    public BorrowDto(Client client, Book book, String dateStart, String dateEnd) {
        this.client = client;
        this.book = book;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getBookTitle() {
        return book.getName();
    }

    public String getAuthor() {
        return book.getAuthor();
    }
}
