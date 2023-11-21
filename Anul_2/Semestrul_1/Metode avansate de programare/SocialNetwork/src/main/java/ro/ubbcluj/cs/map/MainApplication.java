package ro.ubbcluj.cs.map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.domain.validators.FriendshipValidator;
import ro.ubbcluj.cs.map.domain.validators.UserValidator;
import ro.ubbcluj.cs.map.repository.FriendshipDBRepository;
import ro.ubbcluj.cs.map.repository.Repository;
import ro.ubbcluj.cs.map.repository.UserDBRepository;
import ro.ubbcluj.cs.map.service.Service;

import java.io.IOException;

public class MainApplication extends Application {

    private static String url;
    private static String username;
    private static String password;
    private Service service;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public static void main(String[] args) {

        url = args[0];
        username = args[1];
        password = args[2];

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Repository<Long, User> userRepo = new UserDBRepository(url, username, password, new UserValidator());
        Repository<Long, Friendship> friendshipRepo = new FriendshipDBRepository(url, username, password, new FriendshipValidator());
        service = new Service(userRepo, friendshipRepo);

        initView(primaryStage);
        primaryStage.show();

    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);

        Controller appController = fxmlLoader.getController();
        appController.setService(this.service);
        appController.initApp(service.getAllUsers());
    }
}