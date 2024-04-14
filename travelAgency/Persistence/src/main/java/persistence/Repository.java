package app.persistence;


import app.model.Entity;

import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {

    Optional<E> findOne(ID id);
    Iterable<E> findAll();
    void save(E entity) throws SQLException;
    void delete(ID id);
    void update(E entity) throws SQLException;
}


