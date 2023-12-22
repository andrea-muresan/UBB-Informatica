package ro.ubbcluj.cs.map.repository;

import ro.ubbcluj.cs.map.domain.Entity;

public interface MessagePagingRepository<ID, E extends Entity<ID>> extends Repository<ID, E> {
    Page<E> findAll(Pageable pageable, Long user1Id, Long user2Id);
}
