package app.controller;

import app.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController{

    private Service service;

    @FXML
    private PasswordField checkPasswordTxtField;

    @FXML
    private TextField addressTxtField;

    @FXML
    private TextField usernameTxtField;

    @FXML
    private TextField firstNameTxtField;

    @FXML
    private TextField lastNameTxtField;

    @FXML
    private TextField cnpTxtField;

    @FXML
    private PasswordField passwordTxtField;

    @FXML
    private Button signUpBtn;

    public void setService(Service service) {
        this.service = service;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void signUp(MouseEvent event) {
        String firstName = firstNameTxtField.getText();
        String lastName = lastNameTxtField.getText();
        String address = addressTxtField.getText();
        String cnp = cnpTxtField.getText();
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();
        String checkPassword = checkPasswordTxtField.getText();

        if (!password.equals(checkPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error!", "Parolele nu coincide");
        } else {
            service.addUser(username, password, firstName, lastName, cnp, address);
            showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "cont creat cu succes! \n Mergi inapoi la LogIn");
        }
    }

}