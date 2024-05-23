package app.repository;

import app.domain.Client;
import app.domain.Librarian;

public interface ILibrarianRepository extends IRepository<Librarian> {


    Librarian findByCredentials(String username, String password);
}
