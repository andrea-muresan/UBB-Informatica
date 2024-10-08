package ro.mpp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ro.mpp.repository.UserDBRepository;
import ro.mpp.repository.UserRepository;
import ro.mpp.service.ServiceLogIn;
import ro.mpp.service.ServiceSignUp;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class SignUpController {

    private ServiceSignUp service;

    public void setService(ServiceSignUp srv) {
        this.service = srv;
    }

    @FXML
    private PasswordField checkPasswordTxtField;

    @FXML
    private Button logInBtn;

    @FXML
    private PasswordField passwordTxtField;

    @FXML
    private Button signUpBtn;

    @FXML
    private TextField usernameTxtField;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void logInWindow(MouseEvent event) {
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle("Travel Agency");
            stage.setScene(scene);

            UserRepository userRepo=new UserDBRepository(props);
            ServiceLogIn srvLogIn = new ServiceLogIn(userRepo);

            LogInController logInController = fxmlLoader.getController();
            logInController.setService(srvLogIn);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!");
        }
    }

    @FXML
    void signUp(MouseEvent event) throws SQLException {
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();

        if (password.equals(checkPasswordTxtField.getText())) {
            if(service.signUp(username, password)){
                showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "Account created. Go back and log in.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Password and checkPassword are not the same");
        }

    }

}
