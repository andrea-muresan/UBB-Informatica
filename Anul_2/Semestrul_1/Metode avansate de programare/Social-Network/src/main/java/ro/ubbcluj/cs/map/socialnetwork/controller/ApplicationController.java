package ro.ubbcluj.cs.map.socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import ro.ubbcluj.cs.map.socialnetwork.domain.Friendship;
import ro.ubbcluj.cs.map.socialnetwork.domain.Message;
import ro.ubbcluj.cs.map.socialnetwork.domain.User;
import ro.ubbcluj.cs.map.socialnetwork.repository.Page;
import ro.ubbcluj.cs.map.socialnetwork.repository.Pageable;
import ro.ubbcluj.cs.map.socialnetwork.service.Service;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    private Service service;

    private User userLogged;

    private final ObservableList<User> usersObs = FXCollections.observableArrayList();
    private final ObservableList<Friendship> friendshipsObs = FXCollections.observableArrayList();
    private final ObservableList<Friendship> friendRequestsObs = FXCollections.observableArrayList();
    private final ObservableList<Message> messagesObs = FXCollections.observableArrayList();


    // Pagination variables
    private int currentPageUsers = 0;
    private int pageSizeUsers = 5;

    private int currentPageFriendships = 0;
    private int pageSizeFriendships = 5;

    private int currentPageFriendRequests = 0;
    private int pageSizeFriendRequests = 5;

    private int currentPageMessages = 0;
    private int pageSizeMessages = 5;


    // Friendships Window
    @FXML
    private Button nextBtnFriendships;
    @FXML
    private Button previousBtnFriendships;
    @FXML
    private Button lastPageBtnFriendships;
    @FXML
    private Button firstPageBtnFriendships;
    @FXML
    private TextField noElementsOnPageFriendships;
    @FXML
    private Label pageNumberFriendships;
    @FXML
    private ListView<Friendship> listOfFriendships;

    public void setService(Service service) {
        this.service = service;
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }

    public void initApp() {
        listOfFriendships.getItems().clear();
//        listOfFriendRequests.getItems().clear();

        // Friendships
        Page<Friendship> pageFriendships = service.findAllFriendships(new Pageable(currentPageFriendships, pageSizeFriendships));

        int maxPageFriendships = (int) Math.ceil((double) pageFriendships.getTotalElementCount() / pageSizeFriendships) - 1;
        if (maxPageFriendships == -1) maxPageFriendships = 0;
        if (currentPageFriendships > maxPageFriendships) {
            currentPageFriendships = maxPageFriendships;
            pageFriendships = service.findAllFriendships(new Pageable(currentPageFriendships, pageSizeFriendships));
        }

        int totalNumberOfElementsFriendships = pageFriendships.getTotalElementCount();

        previousBtnFriendships.setDisable(currentPageFriendships == 0);
        firstPageBtnFriendships.setDisable(currentPageFriendships == 0);
        nextBtnFriendships.setDisable((currentPageFriendships + 1) * pageSizeFriendships >= totalNumberOfElementsFriendships);
        lastPageBtnFriendships.setDisable((currentPageFriendships + 1) * pageSizeFriendships >= totalNumberOfElementsFriendships);


        for (Friendship friendship : pageFriendships.getElementsOnPage()) {
            friendshipsObs.add(friendship);
        }
        listOfFriendships.setItems(friendshipsObs);

        pageNumberFriendships.setText((currentPageFriendships + 1) + "/" + (maxPageFriendships + 1));

        // Friend Requests
//        Page<Friendship> pageFriendRequests = service.findAllFriendRequests(new Pageable(currentPageFriendRequests, pageSizeFriendRequests));
//
//        int maxPageFriendRequests = (int) Math.ceil((double) pageFriendRequests.getTotalElementCount() / pageSizeFriendRequests) - 1;
//        if (maxPageFriendRequests == -1) maxPageFriendRequests = 0;
//        if (currentPageFriendRequests > maxPageFriendRequests ) {
//            currentPageFriendRequests = maxPageFriendRequests;
//            pageFriendRequests = service.findAllFriendRequests(new Pageable(currentPageFriendRequests, pageSizeFriendRequests));
//        }
//
//        int totalNumberOfElementsFriendRequests = pageFriendRequests.getTotalElementCount();
//
//        previousBtnFriendRequests.setDisable(currentPageFriendRequests == 0);
//        firstPageBtnFriendRequests.setDisable(currentPageFriendRequests == 0);
//        nextBtnFriendRequests.setDisable((currentPageFriendRequests + 1) * pageSizeFriendRequests >= totalNumberOfElementsFriendRequests);
//        lastPageBtnFriendRequests.setDisable((currentPageFriendRequests + 1) * pageSizeFriendRequests >= totalNumberOfElementsFriendRequests);
//
//        for (Friendship friendship : pageFriendRequests.getElementsOnPage()) {
//            friendRequestsObs.add(friendship);
//        }
//        listOfFriendRequests.setItems(friendRequestsObs);
//
//        pageNumberFriendRequests.setText((currentPageFriendRequests + 1) + "/" + (maxPageFriendRequests + 1));
    }

    public void deleteFriendship(MouseEvent mouseEvent) {
        if (listOfFriendships.getSelectionModel().getSelectedItem() != null) {
            Friendship friendship = listOfFriendships.getSelectionModel().getSelectedItem();
            service.getFriendshipRepo().delete(friendship.getId());
            initApp();
        }
    }

    public void onPreviousFriendships(MouseEvent mouseEvent) {
        currentPageFriendships--;
        initApp();
    }

    public void onNextFriendships(MouseEvent mouseEvent) {
        currentPageFriendships++;
        initApp();
    }

    public void onFirstFriendships(MouseEvent mouseEvent) {
        currentPageFriendships = 0;
        initApp();
    }

    public void onLastFriendships(MouseEvent mouseEvent) {
        Page<Friendship> pageFriendships = service.findAllFriendships(new Pageable(currentPageFriendships, pageSizeFriendships));
        currentPageFriendships = (int) Math.ceil((double) pageFriendships.getTotalElementCount() / pageSizeFriendships) - 1;
        initApp();
    }

    public void setNoElementsOnPageFriendships(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER)) {
            try {
                if (!Objects.equals(noElementsOnPageFriendships.getText(), "0"))
                    pageSizeFriendships = Integer.parseInt(noElementsOnPageFriendships.getText());
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("!!!");
                errorAlert.setContentText("Something wrong");
                errorAlert.showAndWait();
            }
            initApp();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOfFriendships.setItems(friendshipsObs);
//        listOfFriendRequests.setItems(friendRequestsObs);
    }
}
