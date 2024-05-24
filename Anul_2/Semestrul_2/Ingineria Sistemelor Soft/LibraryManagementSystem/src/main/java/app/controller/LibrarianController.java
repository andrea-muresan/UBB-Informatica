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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LibrarianController implements Initializable, Observer {
    private Service service;
    private User librarian;

    private final ObservableList<BookSet> bookSetObs = FXCollections.observableArrayList();
    private final ObservableList<BorrowDto> landingsObs = FXCollections.observableArrayList();

    public LibrarianController() {

    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        // initBooksView();
    }

    public void setLibrarian(User librarian) {
        this.librarian = librarian;
    }

    @FXML
    private TableView<BookSet> booksView;

    @FXML
    private TextField genreTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField authorTxt;

    @FXML
    private TextField isbnTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField languageTxt;

    @FXML
    private TextField idLandingTxt;

    @FXML
    private TableView<BorrowDto> landingsView;

    @FXML
    void returnBook(MouseEvent event) {
        String id = idLandingTxt.getText();
        try {
            service.returnBook(id);
            setLandingsView();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void addBook(MouseEvent event) {
        String title = titleTxt.getText();
        String author = authorTxt.getText();
        String genre = genreTxt.getText();
        String isbn = isbnTxt.getText();
        String language = languageTxt.getText();

        try {
            service.addBook(title, author, genre, isbn, language);
//            setBooksView();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void deleteBook(MouseEvent event) {
        String id = idTxt.getText();
        try {
            service.deleteBook(id);
            setBooksView();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void getBookDetailsTable(MouseEvent event) {
        BookSet bkSet = booksView.getSelectionModel().getSelectedItem();
        if (bkSet != null) {
            titleTxt.setText(bkSet.getName());
            authorTxt.setText(bkSet.getAuthor());
            isbnTxt.setText(bkSet.getISBN());
            genreTxt.setText(bkSet.getGenre());
            languageTxt.setText(bkSet.getLanguage());

            idTxt.setText(bkSet.getId().toString());
        }

    }

    @FXML
    void clearFields(MouseEvent event) {
        titleTxt.clear();
        authorTxt.clear();
        isbnTxt.clear();
        genreTxt.clear();
        languageTxt.clear();

        idTxt.clear();
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void initView() {
        initBooksView();
        initLandingsView();
    }

    public void initLandingsView() {
        var clientCol = new TableColumn<BorrowDto, String>("CLIENT");
        clientCol.setMinWidth(150);
        clientCol.setCellValueFactory(bkS -> {
            Client cl = bkS.getValue().getClient();
            return new SimpleStringProperty(
                "ID: " + cl.getId() + "; NUME: " + cl.getFirstName() + " " + cl.getLastName()
            );
        });

        var bookCol = new TableColumn<BorrowDto, String>("CARTE");
        bookCol.setMinWidth(300);
        bookCol.setCellValueFactory(bkS -> {
            Book bk = bkS.getValue().getBook();
            return new SimpleStringProperty(
                    "ID: " +bk.getId() + "; CARTE: " + bk.getName() + "; AUTOR: " + bk.getAuthor()
            );
        });

        landingsView.getColumns().addAll(clientCol, bookCol);
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
    private void setBooksView() {
        try {
            bookSetObs.clear();
            for (BookSet book : service.findAllBooks()) {
                bookSetObs.add(book);
            }

            booksView.setItems(bookSetObs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }

        setLandingsView();
    }

    private void setLandingsView() {
        try {
            landingsObs.clear();
            landingsObs.addAll(service.findAllLandings());

            landingsView.setItems(landingsObs);
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
        setLandingsView();
    }
}
