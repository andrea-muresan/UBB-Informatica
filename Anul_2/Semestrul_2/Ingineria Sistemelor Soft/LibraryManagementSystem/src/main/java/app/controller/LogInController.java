package app.controller;

import app.domain.Librarian;
import app.domain.User;
import app.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LogInController {
    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private Button closeAppBtn;

    @FXML
    private TextField usernameTxtField;

    @FXML
    private Hyperlink signUpHLink;

    @FXML
    private Button logInBtn;

    @FXML
    private PasswordField passwordTxtField;

    @FXML
    void closeApp(MouseEvent event) {
        System.exit(0);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void signUpWindow(ActionEvent event) {

    }

    @FXML
    void handleLogIn(MouseEvent event) {
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();

        try{
            User crtUser = service.findByCredentials(username, password);
            FXMLLoader stageLoader = new FXMLLoader();

            if (crtUser instanceof Librarian) {
                stageLoader.setLocation(getClass().getResource("/librarianApp.fxml"));

//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Stage stage = new Stage();

                Scene scene = new Scene(stageLoader.load(), 600, 400);
                stage.setScene(scene);
                stage.setTitle("Librarian: " + crtUser.getUsername() + " connected");

                LibrarianController lbController = stageLoader.getController();

                lbController.setService(service);
                lbController.setLibrarian(crtUser);

                // lbController.initApp();
                stage.show();
            } else {
                stageLoader.setLocation(getClass().getResource("/clientApp.fxml"));

//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Stage stage = new Stage();
                stage.setTitle("Reader: " + crtUser.getUsername() + " connected");

                Scene scene = new Scene(stageLoader.load(), 600, 400);
                stage.setScene(scene);

                ClientController clController = stageLoader.getController();

                clController.setService(service);
                clController.setClient(crtUser);
                //lbController.setStage(stage);

                // lbController.initApp();
                stage.show();
            }
        }
        catch(Exception e){
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }

    }
}
