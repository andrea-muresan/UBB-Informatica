package ro.mpp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp.controller.LogInController;
import ro.mpp.domain.User;
import ro.mpp.repository.UserDBRepository;
import ro.mpp.repository.UserRepository;
import ro.mpp.service.ServiceLogIn;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        initView(stage);
        stage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        primaryStage.setTitle("Travel Agency");
        primaryStage.setScene(scene);

        UserRepository userRepo=new UserDBRepository(props);
        ServiceLogIn srvLogIn = new ServiceLogIn(userRepo);

        LogInController logInController = fxmlLoader.getController();
        logInController.setService(srvLogIn);
    }
}