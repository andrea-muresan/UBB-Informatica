package app.repository;

import app.domain.BookSet;

public interface IBookSetRepository extends IRepository<BookSet>{
    void MoveBookToUnavailable();
    void moveBookToAvailable();
}
