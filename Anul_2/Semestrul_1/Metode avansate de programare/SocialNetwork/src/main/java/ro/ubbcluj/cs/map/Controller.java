package ro.ubbcluj.cs.map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.service.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TextField userIdUpdate;
    private Service service;
    @FXML
    private TextField emailAdd;

    @FXML
    private TextField userIdSearch;

    @FXML
    private TextField firstNameAdd;

    @FXML
    private Tab friendshipsWindow;

    @FXML
    private TextField lastNameAdd;

    @FXML
    private ListView<User> listOfUsers;

    private ObservableList<User> usersObs = FXCollections.observableArrayList();


    @FXML
    private Tab usersWindow;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void initApp(Iterable<User> users) {
        listOfUsers.getItems().clear();

        for (User user : users) {
            usersObs.add(user);
        }
    }

    @FXML
    void addUser(MouseEvent event) {
        String firstName = firstNameAdd.getText();
        String lastName = lastNameAdd.getText();
        String email = emailAdd.getText();

        if (!service.addUser(firstName, lastName, email)) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Invalid user");
            errorAlert.showAndWait();
        };

        firstNameAdd.clear();
        lastNameAdd.clear();
        emailAdd.clear();

        initApp(service.getAllUsers());
    }

    @FXML
    void deleteUser(MouseEvent event) {
        if (listOfUsers.getSelectionModel().getSelectedItem() != null) {
            User user = listOfUsers.getSelectionModel().getSelectedItem();
            service.deleteUser(user.getEmail());
            initApp(service.getAllUsers());
        }
    }

    @FXML
    void searchUser(MouseEvent event) {
        String id = userIdSearch.getText();
        User u = service.findUser(id);

        if (u != null) {
            List<User> users = new ArrayList<>();
            users.add(u);

            initApp(users);
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText("!!!");
            errorAlert.setContentText("User not found");
            errorAlert.showAndWait();
        }

        userIdSearch.clear();
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listOfUsers.setItems(usersObs);

    }

    public void reloadList(MouseEvent mouseEvent) {
        initApp(service.getAllUsers());
    }

    public void updateUser(MouseEvent mouseEvent) {
        String id = userIdUpdate.getText();
        String firstName = firstNameAdd.getText();
        String lastName = lastNameAdd.getText();
        String email = emailAdd.getText();

        if (!service.updateUser(id, firstName, lastName, email)){
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setHeaderText("!!!");
            errorAlert.setContentText("Something went wrong");
            errorAlert.showAndWait();
        };

        userIdUpdate.clear();
        firstNameAdd.clear();
        lastNameAdd.clear();
        emailAdd.clear();

        initApp(service.getAllUsers());
    }
}

