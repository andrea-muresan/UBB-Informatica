package ro.mpp.service;

import ro.mpp.domain.User;
import ro.mpp.repository.UserDBRepository;
import ro.mpp.repository.UserRepository;

import java.sql.SQLException;
import java.util.Objects;

public class ServiceSignUp {
    private UserRepository userRepo;

    public ServiceSignUp(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean signUp(String username, String password) throws SQLException {
        if (!Objects.equals(username, "") && !Objects.equals(password, "")) {
            userRepo.save(new User(username, password));
            return true;
        }
        return false;
    }
}
