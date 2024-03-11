package ro.mpp.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight extends Entity<Long>{
    private String destination;
    private LocalDate date;
    private LocalTime hour;
    private String airport;
    private int no_seats;

    public Flight(String destination, LocalDate date, LocalTime hour, String airport, int no_seats) {
        this.destination = destination;
        this.date = date;
        this.hour = hour;
        this.airport = airport;
        this.no_seats = no_seats;
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

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public int getNo_seats() {
        return no_seats;
    }

    public void setNo_seats(int no_seats) {
        this.no_seats = no_seats;
    }
}
