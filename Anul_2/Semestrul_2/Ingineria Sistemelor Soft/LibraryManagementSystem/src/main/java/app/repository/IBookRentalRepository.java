package app.repository;

import app.domain.BookRental;

import java.util.List;

public interface IBookRentalRepository extends IRepository<BookRental>{

    List<BookRental> getAllNotReturned();

    BookRental findByUserAndBook(Integer userId, Integer bookId);
}
