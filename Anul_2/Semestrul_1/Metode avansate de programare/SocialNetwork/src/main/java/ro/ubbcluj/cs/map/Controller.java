package ro.ubbcluj.cs.map;

import com.sun.javafx.fxml.LoadListener;
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
import ro.ubbcluj.cs.map.domain.Message;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.service.Service;

import java.net.URL;
import java.util.*;


public class Controller implements Initializable {

    private Service service;

    // Lists
    private final ObservableList<User> usersObs = FXCollections.observableArrayList();
    private final ObservableList<Friendship> friendshipsObs = FXCollections.observableArrayList();
    private final ObservableList<Friendship> friendRequestsObs = FXCollections.observableArrayList();
    private final ObservableList<Message> messagesObs = FXCollections.observableArrayList();

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

    // Message window
    @FXML
    private ListView<Message> listOfMessages;
    @FXML
    private TextField message;
    @FXML
    private TextField sendEmailFrom;
    @FXML
    private TextField sendEmailTo;
    @FXML
    private TextField showMessagesEmail1;
    @FXML
    private TextField showMessagesEmail2;


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

    public void sendMessage(MouseEvent mouseEvent) {
        String emailFrom = sendEmailFrom.getText();
        String emailTo = sendEmailTo.getText();
        List<String> toUsers = new ArrayList<>(Arrays.asList(emailTo.split(" ")));
        String msg = message.getText();

        if (!service.addMessage(emailFrom, toUsers, msg)) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("!!!");
            errorAlert.setContentText("Something wrong");
            errorAlert.showAndWait();
        } else {
            loadListOfMessages(emailFrom, toUsers.get(0));
        }

        message.clear();

    }

    public void loadListOfMessages(String emailFrom, String emailTo) {
        listOfMessages.getItems().clear();
        messagesObs.clear();
        for (Message msg : service.getMessagesBetweenTwoUsers(emailFrom, emailTo)) {
            messagesObs.add(msg);
        }
        if (messagesObs.isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setHeaderText(":((");
            errorAlert.setContentText("No messages");
            errorAlert.showAndWait();
        }

        listOfMessages.setItems(messagesObs);
    }

    public void searchMessages(MouseEvent mouseEvent) {
        String email1 = showMessagesEmail1.getText();
        String email2 = showMessagesEmail2.getText();

        showMessagesEmail1.clear();
        showMessagesEmail2.clear();

        sendEmailFrom.setText(email1);
        sendEmailTo.setText(email2);

        loadListOfMessages(email1, email2);
    }


    public void replyMessage(MouseEvent mouseEvent) {
        if (listOfMessages.getSelectionModel().getSelectedItem() != null) {
            Message msg = listOfMessages.getSelectionModel().getSelectedItem();

            String emailFrom = sendEmailFrom.getText();
            String emailTo = sendEmailTo.getText();
            List<String> toUsers = new ArrayList<>(Collections.singletonList(emailTo));
            String replyText = message.getText();

            if (!service.addMessage(emailFrom, toUsers, replyText, msg)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("!!!");
                errorAlert.setContentText("Something wrong");
                errorAlert.showAndWait();
            } else {
                loadListOfMessages(emailFrom, toUsers.get(0));
            }

            message.clear();
        }

    }
}
