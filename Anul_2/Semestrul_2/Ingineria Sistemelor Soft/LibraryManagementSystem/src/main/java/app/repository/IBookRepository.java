package app.repository;

import app.domain.Book;

public interface IBookRepository extends IRepository<Book>{
    Book findBookAvailableISBN(String isbn);
}
