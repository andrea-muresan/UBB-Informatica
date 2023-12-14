package ro.ubbcluj.cs.map.repository;

import ro.ubbcluj.cs.map.domain.Entity;

public interface PagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable);
}
