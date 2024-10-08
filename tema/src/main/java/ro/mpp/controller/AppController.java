package ro.mpp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ro.mpp.domain.Flight;
import ro.mpp.service.ServiceApp;
import ro.mpp.service.ServiceLogIn;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppController {

    private ServiceApp service;

    private final ObservableList<Flight> flightsObs = FXCollections.observableArrayList();
    private final ObservableList<Flight> searchFlightsObs = FXCollections.observableArrayList();

    public void setService(ServiceApp service) {
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
    void buyTicket(MouseEvent event) {
        String name = clientNameTxtField.getText();
        String address = clientAddressTxtField.getText();
        String tourists = touristsNamesTxtField.getText();
        String noSeats = noSeatsTxtField.getText();

        Flight flight = searchView.getSelectionModel().getSelectedItem();
        if (flight != null) {
            String endString = service.buyTicket(flight, name, address, tourists, noSeats);

            if (endString.equals("Success")) {
                showAlert(Alert.AlertType.CONFIRMATION, "Confirmation", "The tickets are bought");
                setFlightsView();
                setSearchView(flight.getDestination(), flight.getDate());
            }
            else {
                showAlert(Alert.AlertType.WARNING, "Error", endString);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Error", "No flight selected");
        }


    }

    @FXML
    void logOut(MouseEvent event) {
        stage.close();
    }

    @FXML
    void searchFlights(MouseEvent event) {
        String dest = destinationTxtField.getText();
        LocalDate date = datePicker.getValue();

        setSearchView(dest, date);
    }

    private void setFlightsView() {
        flightsObs.clear();
        for (Flight flight : service.getAllFlights()) {
            if (flight.getNoSeats() != 0)
                flightsObs.add(flight);
        }

        flightsView.setItems(flightsObs);

    }

    private void setSearchView(String dest, LocalDate date) {
        searchFlightsObs.clear();
        for (Flight flight : service.getFlightsByDestinationDate(dest, date)) {
            if (flight.getNoSeats() != 0)
                searchFlightsObs.add(flight);
        }

        searchView.setItems(searchFlightsObs);

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
}
