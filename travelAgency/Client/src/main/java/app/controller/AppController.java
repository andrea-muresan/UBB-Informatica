package app.controller;

import app.model.Flight;
import app.model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Objects;

public class AppController implements IObserver {

    private IServices service;

    private User userLogged;

    private final ObservableList<Flight> flightsObs = FXCollections.observableArrayList();
    private final ObservableList<Flight> searchFlightsObs = FXCollections.observableArrayList();

    public void setService(IServices service) {
        this.service = service;
    }

    // Reference to the stage
    private Stage stage;

    // Method to set the stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField clientAddressTxtField;

    @FXML
    private TextField clientNameTxtField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField destinationTxtField;

    @FXML
    private TableView<Flight> flightsView;

    @FXML
    private TextField noSeatsTxtField;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<Flight> searchView;

    @FXML
    private TextField touristsNamesTxtField;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void buyTicket(MouseEvent event) throws MyException, SQLException {
        String name = clientNameTxtField.getText();
        String address = clientAddressTxtField.getText();
        String tourists = touristsNamesTxtField.getText();
        String noSeats = noSeatsTxtField.getText();

        Flight flight = searchView.getSelectionModel().getSelectedItem();
        if (flight != null) {
            try {
                service.buyTicket(flight, name, address, tourists, noSeats);

                showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "The tickets are bought");
            } catch (MyException e) {
                showAlert(Alert.AlertType.WARNING, "Error", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Error", "No flight selected");
        }


    }

    @FXML
    void logOut(MouseEvent event) {
        logout();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    void logout() {
        try {
            service.logOut(userLogged, this);
        } catch (Exception e) {
            System.out.println("Logout error " + e);
        }

    }

    @FXML
    void searchFlights(MouseEvent event) {
        String dest = destinationTxtField.getText();
        LocalDate date = datePicker.getValue();

        try {
            setSearchView(dest, date);
        } catch (MyException e) {
            showAlert(Alert.AlertType.WARNING, "Error", "Something went wrong");
        }

    }

    private void setFlightsView() {

        try {
            flightsObs.clear();
            for (Flight flight : service.getAllFlights()) {
                if (flight.getNoSeats() != 0)
                    flightsObs.add(flight);
            }

            flightsView.setItems(flightsObs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }


    }

    private void setSearchView(String dest, LocalDate date) throws MyException {
        try {
            searchFlightsObs.clear();
            for (Flight flight : service.getFlightsByDestinationDate(dest, date)) {
                if (flight.getNoSeats() != 0)
                    searchFlightsObs.add(flight);
            }

            searchView.setItems(searchFlightsObs);
        } catch (Exception e){
           throw e;
        }
    }

    public void initFlightsView() {
        var destinationCol = new TableColumn("Destination");
        destinationCol.setMinWidth(100);
        destinationCol.setCellValueFactory(
                new PropertyValueFactory<Flight, String>("destination"));

        var dateCol = new TableColumn("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Flight, LocalDate>("date"));

        var timeCol = new TableColumn("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(
                new PropertyValueFactory<Flight, LocalTime>("hour"));

        var airportCol = new TableColumn("Airport");
        airportCol.setMinWidth(100);
        airportCol.setCellValueFactory(
                new PropertyValueFactory<Flight, String>("airport"));

        var noSeatsCol = new TableColumn("No. seats");
        noSeatsCol.setMinWidth(100);
        noSeatsCol.setCellValueFactory(
                new PropertyValueFactory<Flight, Integer>("noSeats"));

        flightsView.getColumns().addAll(destinationCol, dateCol, timeCol, airportCol, noSeatsCol);
        setFlightsView();
    }

    public void initSearchView() {
        var destinationCol = new TableColumn("Destination");
        destinationCol.setMinWidth(100);
        destinationCol.setCellValueFactory(
                new PropertyValueFactory<Flight, String>("destination"));

        var dateCol = new TableColumn("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(
                new PropertyValueFactory<Flight, LocalDate>("date"));

        var timeCol = new TableColumn("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(
                new PropertyValueFactory<Flight, LocalTime>("hour"));

        var airportCol = new TableColumn("Airport");
        airportCol.setMinWidth(100);
        airportCol.setCellValueFactory(
                new PropertyValueFactory<Flight, String>("airport"));

        var noSeatsCol = new TableColumn("No. seats");
        noSeatsCol.setMinWidth(100);
        noSeatsCol.setCellValueFactory(
                new PropertyValueFactory<Flight, Integer>("noSeats"));

        searchView.getColumns().addAll(destinationCol, timeCol, noSeatsCol);
    }
    public void initApp() {
        initFlightsView();
        initSearchView();
    }

    public void setUserLogged(User crtUser) {
        this.userLogged = crtUser;
    }

    @Override
    public void updateFlight(Flight flight) {
        Platform.runLater(()->{
            setFlightsView();
            try {
                int ok = 0;
                Iterator<Flight> flightIter = searchFlightsObs.iterator();
                while (flightIter.hasNext()) {
                    Flight fl = flightIter.next();
                    if (Objects.equals(fl.getId(), flight.getId())) ok = 1;
                }
                if (ok == 1)
                    setSearchView(flight.getDestination(), flight.getDate());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }

        });
    }

    public void logOut2() {
        stage.close();
    }
}
