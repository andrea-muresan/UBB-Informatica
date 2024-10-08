package persistence;


import app.model.Entity;

public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);
    Iterable<E> findAll();
    E save(E entity);
    void delete(ID id);
    void update(E entity);
}


