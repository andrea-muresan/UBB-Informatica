package app.controller;

import app.domain.Book;
import app.domain.Client;
import app.domain.Librarian;
import app.domain.User;
import app.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientController implements Initializable {
    private Service service;
    private User client;

    private final ObservableList<Book> booksObs = FXCollections.observableArrayList();

    public ClientController() {

    }

    public void setService(Service service) {
        this.service = service;
        // initBooksView();
    }

    public void setClient(User client) {
        this.client = client;
    }

    @FXML
    private TableView<Book> booksView;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void initBooksView() {
        var titleCol = new TableColumn("TITLU");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("name"));

        var authorCol = new TableColumn("AUTOR");
        authorCol.setMinWidth(100);
        authorCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("author"));

        var genreCol = new TableColumn("GEN");
        genreCol.setMinWidth(100);
        genreCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("genre"));

        var languageCol = new TableColumn("LIMBA");
        languageCol.setMinWidth(100);
        languageCol.setCellValueFactory(
                new PropertyValueFactory<Book, String>("language"));

        var noCopiesCol = new TableColumn("NR. EXEMPLARE");
        noCopiesCol.setMinWidth(100);
        noCopiesCol.setCellValueFactory(
                new PropertyValueFactory<Book, Integer>("noCopies"));



        booksView.getColumns().addAll(titleCol, authorCol, genreCol, languageCol, noCopiesCol);
        setBooksView();
    }

    private void setBooksView() {
        try {
            booksObs.clear();
            for (Book book : service.findAllBooks()) {
                booksObs.add(book);
            }

            booksView.setItems(booksObs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(this::initBooksView, 1, TimeUnit.SECONDS);
    }
}
