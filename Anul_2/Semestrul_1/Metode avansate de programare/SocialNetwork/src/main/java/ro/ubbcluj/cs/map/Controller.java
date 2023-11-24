package ro.ubbcluj.cs.map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import ro.ubbcluj.cs.map.domain.FriendRequest;
import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.service.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private Service service;

    // Lists
    private final ObservableList<User> usersObs = FXCollections.observableArrayList();
    private final ObservableList<Friendship> friendshipsObs = FXCollections.observableArrayList();
    private final ObservableList<Friendship> friendRequestsObs = FXCollections.observableArrayList();

    // User window
    @FXML
    private ListView<User> listOfUsers;
    @FXML
    public TextField userIdUpdate;
    @FXML
    private TextField emailAdd;
    @FXML
    private TextField userIdSearch;
    @FXML
    private TextField firstNameAdd;
    @FXML
    private TextField lastNameAdd;

    // Friendships Window
    @FXML
    private Tab friendshipsWindow;
    @FXML
    private ListView<Friendship> listOfFriendships;

    // Friend Request Window
    @FXML
    private ListView<Friendship> listOfFriendRequests;
    @FXML
    private TextField friendRequestEmail1;
    @FXML
    private TextField friendRequestEmail2;


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listOfUsers.setItems(usersObs);
        listOfFriendships.setItems(friendshipsObs);
        listOfFriendRequests.setItems(friendRequestsObs);
    }

    public void initApp(Iterable<User> users) {
        listOfUsers.getItems().clear();
        listOfFriendships.getItems().clear();
        listOfFriendRequests.getItems().clear();

        for (User user : users) {
            usersObs.add(user);
        }

        for (Friendship friendship : service.getAllFriendships()) {
            if (friendship.getFriendRequestStatus() == FriendRequest.ACCEPTED) {
                friendshipsObs.add(friendship);
            }
            friendRequestsObs.add(friendship);
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
        }
        ;

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

    public void reloadList(MouseEvent mouseEvent) {
        initApp(service.getAllUsers());
    }

    public void updateUser(MouseEvent mouseEvent) {
        String id = userIdUpdate.getText();
        String firstName = firstNameAdd.getText();
        String lastName = lastNameAdd.getText();
        String email = emailAdd.getText();

        if (!service.updateUser(id, firstName, lastName, email)) {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setHeaderText("!!!");
            errorAlert.setContentText("Something went wrong");
            errorAlert.showAndWait();
        }

        userIdUpdate.clear();
        firstNameAdd.clear();
        lastNameAdd.clear();
        emailAdd.clear();

        initApp(service.getAllUsers());
    }

    public void createFriendRequest(MouseEvent mouseEvent) {
        String email1 = friendRequestEmail1.getText();
        String email2 = friendRequestEmail2.getText();

        if (!service.createFriendRequest(email1, email2)) {
            Alert errorAlert = new Alert(Alert.AlertType.WARNING);
            errorAlert.setHeaderText("!!!");
            errorAlert.setContentText("Something went wrong");
            errorAlert.showAndWait();
        }

        friendRequestEmail1.clear();
        friendRequestEmail2.clear();
        initApp(service.getAllUsers());
    }

    public void acceptFriendRequest(MouseEvent mouseEvent) {
        if (listOfFriendRequests.getSelectionModel().getSelectedItem() != null) {
            Friendship friendship = listOfFriendRequests.getSelectionModel().getSelectedItem();
            service.respondFriendRequest(friendship, FriendRequest.ACCEPTED);
            initApp(service.getAllUsers());
        }
        initApp(service.getAllUsers());
    }

    public void rejectFriendRequest(MouseEvent mouseEvent) {
        if (listOfFriendRequests.getSelectionModel().getSelectedItem() != null) {
            Friendship friendship = listOfFriendRequests.getSelectionModel().getSelectedItem();
            service.respondFriendRequest(friendship, FriendRequest.REJECTED);
            initApp(service.getAllUsers());
        }
        initApp(service.getAllUsers());
    }

    public void deleteFriendship(MouseEvent mouseEvent) {
        if (listOfFriendships.getSelectionModel().getSelectedItem() != null) {
            Friendship friendship = listOfFriendships.getSelectionModel().getSelectedItem();
            service.getFriendshipRepo().delete(friendship.getId());
            initApp(service.getAllUsers());
        }
    }

    public void deleteFriendRequest(MouseEvent mouseEvent) {
        if (listOfFriendRequests.getSelectionModel().getSelectedItem() != null) {
            Friendship friendship = listOfFriendRequests.getSelectionModel().getSelectedItem();
            service.getFriendshipRepo().delete(friendship.getId());
            initApp(service.getAllUsers());
        }
    }
}

