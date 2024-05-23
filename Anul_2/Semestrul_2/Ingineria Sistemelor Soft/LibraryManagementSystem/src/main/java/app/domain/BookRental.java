package app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.time.LocalDate;

@jakarta.persistence.Entity
@Table( name = "book_rental" )
public class BookRental extends Entity{
    @Column(name = "user_id")
    private int userId;
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "date_start")
    private String dateStart;
    @Column(name = "date_end")
    private String dateEnd;

    @Column(name = "returned")
    private int returned;

    public BookRental(int userId, int bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public BookRental(int userId, int bookId, String dateStart, String dateEnd, int returned) {
        this.userId = userId;
        this.bookId = bookId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.returned = returned;
    }

    public BookRental() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
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

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "BookRental{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", dateStart='" + dateStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                '}';
    }
}
