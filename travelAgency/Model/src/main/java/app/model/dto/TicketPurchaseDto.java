package app.model.dto;

import app.model.Flight;

import java.io.Serializable;
import java.util.Objects;

public class TicketPurchaseDto implements Serializable {
    private Flight flight;
    private String noOfTickets;
    private String clientName;

    private String touristsNames;

    private String clientAddress;

    public TicketPurchaseDto(Flight flight, String noOfTickets, String clientName, String touristsNames, String clientAddress) {
        this.flight = flight;
        this.noOfTickets = noOfTickets;
        this.clientName = clientName;
        this.touristsNames = touristsNames;
        this.clientAddress = clientAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketPurchaseDto that = (TicketPurchaseDto) o;
        return noOfTickets == that.noOfTickets && Objects.equals(flight, that.flight) && Objects.equals(clientName, that.clientName) && Objects.equals(touristsNames, that.touristsNames) && Objects.equals(clientAddress, that.clientAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, noOfTickets, clientName, touristsNames, clientAddress);
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(String noOfTickets) {
        this.noOfTickets = noOfTickets;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTouristsNames() {
        return touristsNames;
    }

    public void setTouristsNames(String touristsNames) {
        this.touristsNames = touristsNames;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
}