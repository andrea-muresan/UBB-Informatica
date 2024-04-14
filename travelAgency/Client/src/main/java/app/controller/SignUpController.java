package app.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import services.IServices;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class SignUpController {

    private IServices service;

    public void setService(IServices srv) {
        this.service = srv;
    }

    private Parent parentLogIn;

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

    public void setParentLogIn(Parent parentLogIn) {
        this.parentLogIn = parentLogIn;
    }

    @FXML
    void logInWindow(MouseEvent event) {


//        Properties props=new Properties();
//        try {
//            props.load(new FileReader("bd.config"));
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config "+e);
//        }
//
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logIn.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//
//            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//
//            stage.setTitle("Travel Agency");
//            stage.setScene(scene);
//
//            LogInController logInController = fxmlLoader.getController();
//            logInController.setServer(service);
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong!");
//        }
        try{
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(parentLogIn));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    // appController.logout();
                    System.exit(0);
                }
            });

            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
//            appController.initialize1();
        }
        catch(Exception e){

            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
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
