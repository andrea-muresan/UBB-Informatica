package ro.mpp.repository;

import ro.mpp.domain.User;

public interface UserRepository extends Repository<Integer, User> {

    User findByUsername(String username);
}
