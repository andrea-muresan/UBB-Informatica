package app.controller;

import app.domain.*;
import app.domain.dto.BorrowDto;
import app.service.Service;
import app.service.utils.Observer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientController implements Initializable, Observer {
    private Service service;
    private User client;

    private final ObservableList<BookSet> bookSetObs = FXCollections.observableArrayList();
    private final ObservableList<BorrowDto> bookBorrowsObs = FXCollections.observableArrayList();

    public ClientController() {

    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        // initBooksView();
    }

    public void setClient(User client) {
        this.client = client;
    }

    @FXML
    private Button borrowBook;

    @FXML
    private TableView<BorrowDto> borrowsView;

    @FXML
    void borrowBook(MouseEvent event) {
        BookSet book = booksView.getSelectionModel().getSelectedItem();
        try {
            service.borrowBook(client.getId(), book);
            setBooksView();
            setBorrowsView();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private TableView<BookSet> booksView;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initView() {
        initBooksView();
        initBorrowsView();
    }

    private void initBooksView() {
        var titleCol = new TableColumn<BookSet, String>("TITLU");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        var authorCol = new TableColumn<BookSet, String>("AUTOR");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author"));

        var genreCol = new TableColumn<BookSet, String>("GEN");
        genreCol.setMinWidth(100);
        genreCol.setCellValueFactory(
                new PropertyValueFactory<>("genre"));

        var languageCol = new TableColumn<BookSet, String>("LIMBA");
        languageCol.setMinWidth(100);
        languageCol.setCellValueFactory(
                new PropertyValueFactory<>("language"));

        var noAvailableCopiesCol = new TableColumn<BookSet, Integer>("DISPONIBILE");
        noAvailableCopiesCol.setMinWidth(100);
        noAvailableCopiesCol.setCellValueFactory(
                new PropertyValueFactory<>("noCopiesAvailable"));

        var noCopiesCol = new TableColumn<BookSet, Integer>("TOTAL");
        noCopiesCol.setMinWidth(100);
        noCopiesCol.setCellValueFactory(
                new PropertyValueFactory<>("noCopies"));

        booksView.getColumns().addAll(titleCol, authorCol, genreCol, languageCol, noAvailableCopiesCol, noCopiesCol);
        setBooksView();
    }

    public void initBorrowsView() {
        var titleCol = new TableColumn<BorrowDto, String>("TITLU");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBookTitle()));

        var authorCol = new TableColumn<BorrowDto, String>("AUTOR");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<>("author"));

        var startDateCol = new TableColumn<BorrowDto, String>("DE LA");
        startDateCol.setMinWidth(100);
        startDateCol.setCellValueFactory(
                new PropertyValueFactory<>("dateStart"));

        var endDateCol = new TableColumn<BorrowDto, String>("PANA LA");
        endDateCol.setMinWidth(100);
        endDateCol.setCellValueFactory(
                new PropertyValueFactory<>("dateEnd"));

        borrowsView.getColumns().addAll(titleCol, authorCol, startDateCol, endDateCol);
        setBorrowsView();
    }

    private void setBooksView() {
        try {
            bookSetObs.clear();
            bookSetObs.addAll(service.findAllBooks());

            booksView.setItems(bookSetObs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void setBorrowsView() {
        try {
            bookBorrowsObs.clear();
            bookBorrowsObs.addAll(service.findAllBorrowsClient((Client) client));

            borrowsView.setItems(bookBorrowsObs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(this::initView, 1, TimeUnit.SECONDS);
    }

    @Override
    public void update() {
        setBooksView();
        setBorrowsView();
    }

    @FXML
    void logOut(MouseEvent event) {
        service.removeObserver(this);
        service.logOut(client.getId());
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
