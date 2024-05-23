package app.repository;

import app.domain.Entity;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<E extends Entity> {

    List<E> getAll();
}
