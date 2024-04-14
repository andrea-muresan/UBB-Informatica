package app.client.controller;

import app.services.IServices;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LogInController {

    private IServices service;

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

    public void setService(ServiceLogIn service) {
        this.service = service;
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
        try {
            Properties props = new Properties();
            try {
                props.load(new FileReader("bd.config"));
            } catch (IOException e) {
                System.out.println("Cannot find bd.config " + e);
            }

            FXMLLoader stageLoader = new FXMLLoader();
            stageLoader.setLocation(getClass().getResource("/signUp.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(stageLoader.load(), 600, 400);
            stage.setScene(scene);

            SignUpController signUpController = stageLoader.getController();

            UserDBRepository userRepo = new UserDBRepository(props);
            ServiceSignUp serviceSignUp = new ServiceSignUp(userRepo);
            signUpController.setService(serviceSignUp);

            stage.show();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "something went wrong\n");
            System.out.println(e.getMessage());

        }
    }

    @FXML
    void logIn(MouseEvent event) {
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();


        if (service.findUserByCredentials(username, password)) {
            try {
                Properties props = new Properties();
                try {
                    props.load(new FileReader("bd.config"));
                } catch (IOException e) {
                    System.out.println("Cannot find bd.config " + e);
                }

                FXMLLoader stageLoader = new FXMLLoader();
                stageLoader.setLocation(getClass().getResource("/app.fxml"));

//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Stage stage = new Stage();

                Scene scene = new Scene(stageLoader.load(), 600, 400);
                stage.setScene(scene);

                AppController appController = stageLoader.getController();

                FlightRepository flightDBRepository = new FlightDBRepository(props);
                TicketRepository ticketDBRepository = new TicketDBRepository(props);
                ServiceApp serviceApp = new ServiceApp(flightDBRepository, ticketDBRepository);
                appController.setService(serviceApp);
                appController.setStage(stage);

                appController.initApp();
                stage.show();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "something went wrong");
        }
    }

}
