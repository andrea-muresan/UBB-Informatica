package app.persistence;

import app.model.User;

public interface UserRepository extends Repository<Integer, User> {

    User findByUsername(String username);
}
