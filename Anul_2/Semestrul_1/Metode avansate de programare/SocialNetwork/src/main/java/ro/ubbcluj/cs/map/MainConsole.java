package ro.ubbcluj.cs.map;

import ro.ubbcluj.cs.map.console.ConsoleUI;
import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.Message;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.domain.validators.FriendshipValidator;
import ro.ubbcluj.cs.map.domain.validators.UserValidator;
import ro.ubbcluj.cs.map.repository.*;
import ro.ubbcluj.cs.map.service.Service;

public class MainConsole {
    public static void main(String[] args) {
        String url = args[0];
        String username = args[1];
        String password = args[2];

        PagingRepository<Long, User> userRepo = new UserDBRepository(url, username, password, new UserValidator());
        PagingRepository<Long, Friendship> friendshipRepo = new FriendshipDBRepository(url, username, password, new FriendshipValidator());
        Repository<Long, Message> messageRepo = new MessageDBRepository(url, username, password, userRepo);
        Service service = new Service(userRepo, friendshipRepo, messageRepo);
        ConsoleUI console = new ConsoleUI(service);

        console.run();
    }
}
