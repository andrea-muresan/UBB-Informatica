package app.repository;

import app.domain.BookBorrow;

import java.util.List;

public interface IBookBorrowRepository extends IRepository<BookBorrow>{

    List<BookBorrow> getAllNotReturned();

    BookBorrow findByBook(Integer bookId);

    List<BookBorrow> getAllClient(Integer clientId);

}
