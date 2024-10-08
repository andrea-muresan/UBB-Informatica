package app.controller;

import app.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import services.IServices;

public class LogInController {

    private IServices server;
    private AppController appController;

    private SignUpController signUpController;
    private User crtUser;

    private Parent parentApp;

    private Parent parentSignUp;

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

    public void setServer(IServices server) {
        this.server = server;
    }

    public void setParentApp(Parent parentApp) {
        this.parentApp = parentApp;
    }

    public void setParentSignUp(Parent parentSignUp) {
        this.parentSignUp = parentSignUp;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setSignUpController(SignUpController signUpController) {
        this.signUpController = signUpController;
    }

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
//        try {
//            Properties props = new Properties();
//            try {
//                props.load(new FileReader("bd.config"));
//            } catch (IOException e) {
//                System.out.println("Cannot find bd.config " + e);
//            }
//
//            FXMLLoader stageLoader = new FXMLLoader();
//            stageLoader.setLocation(getClass().getResource("/signUp.fxml"));
//
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//            Scene scene = new Scene(stageLoader.load(), 600, 400);
//            stage.setScene(scene);
//
//            SignUpController signUpController = stageLoader.getController();
//
//
//            signUpController.setService(server);
//
//            stage.show();
//
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error", "something went wrong\n");
//            System.out.println(e.getMessage());
//
//        }

        try{
            Stage stage = new Stage();
            stage.setTitle("Sign Up");
            stage.setScene(new Scene(parentSignUp));

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
    void logIn(MouseEvent event) {
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();
        crtUser = new User(username, password);

        try{
            server.findUserByCredentials(username, password, appController);
            Stage stage = new Stage();
            stage.setTitle("Main window for " + crtUser.getUsername());
            stage.setScene(new Scene(parentApp));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    appController.logout();
                    System.exit(0);
                }
            });

            stage.show();
            appController.setUserLogged(crtUser);
            ((Node)(event.getSource())).getScene().getWindow().hide();
//            appController.initialize1();
            appController.initApp();
        }
        catch(Exception e){
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }

    }

}
