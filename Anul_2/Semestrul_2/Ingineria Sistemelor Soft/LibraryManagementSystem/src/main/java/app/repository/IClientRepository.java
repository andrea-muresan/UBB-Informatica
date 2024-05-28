package app.repository;

import app.domain.Client;

import java.sql.SQLException;

public interface IClientRepository extends IRepository<Client> {

    Client findByCredentials(String username, String password);

    Client findByUsername(String username);

    Client findByCNP(String CNP);
}
