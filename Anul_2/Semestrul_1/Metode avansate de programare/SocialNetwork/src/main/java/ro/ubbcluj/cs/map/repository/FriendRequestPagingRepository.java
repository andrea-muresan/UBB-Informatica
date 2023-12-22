package ro.ubbcluj.cs.map.repository;

import ro.ubbcluj.cs.map.domain.Entity;

public interface FriendRequestPagingRepository<ID, E extends Entity<ID>> extends PagingRepository<ID, E>{
    Page<E> findAllFriendRequests(Pageable pageable);
}
