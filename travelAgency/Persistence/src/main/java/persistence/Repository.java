package persistence;


import app.model.Entity;
import app.model.Flight;

import java.sql.SQLException;

public interface Repository<ID, E extends Entity<ID>> {

    Flight findOne(ID id);
    Iterable<E> findAll();
    void save(E entity) throws SQLException;
    void delete(ID id);
    void update(E entity) throws SQLException;
}


