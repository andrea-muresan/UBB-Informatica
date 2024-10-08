package ro.mpp.service;

import ro.mpp.domain.User;
import ro.mpp.repository.UserDBRepository;
import ro.mpp.repository.UserRepository;
import ro.mpp.utils.PassEncTech1;

public class ServiceLogIn {
    private UserRepository userRepo;
    private PassEncTech1 encTech1;

    public ServiceLogIn(UserRepository userRepo) {
        this.userRepo = userRepo;
        encTech1 = new PassEncTech1();
    }

    public boolean findUserByCredentials(String username, String password) {
        User user = userRepo.findByUsername(username);

        if (user != null) {
            if (user.getPassword().equals(encTech1.encrypt(password))) {
                return true;
            }
        }

        return false;
    }
}
