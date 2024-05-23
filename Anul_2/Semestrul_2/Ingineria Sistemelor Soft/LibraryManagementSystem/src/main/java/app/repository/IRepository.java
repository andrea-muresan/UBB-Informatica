package app.repository;

import app.domain.Entity;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<E extends Entity> {
    E findOne(Integer id);
    E save(E entity);
    E update(E entity);
    E delete(Integer id);
    List<E> getAll();
}
