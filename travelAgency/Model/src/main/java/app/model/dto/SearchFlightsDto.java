package app.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class SearchFlightsDto implements Serializable {
    private String destination;
    private LocalDate date;

    public SearchFlightsDto(String destination, LocalDate date) {
        this.destination = destination;
        this.date = date;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SearchFlightsDto{" +
                "destination='" + destination + '\'' +
                ", date=" + date +
                '}';
    }
}