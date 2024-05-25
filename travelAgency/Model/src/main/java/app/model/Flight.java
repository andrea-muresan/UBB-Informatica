package app.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Flight extends Entity<Integer>{
    private String destination;
    private LocalDate date;
    private LocalTime hour;
    private String airport;
    private int NoSeats;

    public Flight(String destination, LocalDate date, LocalTime hour, String airport, int no_seats) {
        this.destination = destination;
        this.date = date;
        this.hour = hour;
        this.airport = airport;
        this.NoSeats = no_seats;
    }

    public Flight(){
        this.destination = null;
        this.date = null;
        this.hour = null;
        this.airport = null;
        this.NoSeats = 0;
    }

    public Flight(String destination, String date, String time, String airport, int noSeats) {
        this.destination = destination;
        // Define the formatter with the expected input format
        DateTimeFormatter formatterD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(date, formatterD);
        DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.hour = LocalTime.parse(time, formatterT);
        this.airport = airport;
        this.NoSeats = noSeats;
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

    public int getNoSeats() {
        return NoSeats;
    }

    public void setNoSeats(int noSeats) {
        this.NoSeats = noSeats;
    }

    @Override
    public String toString() {
        return "Flight{" +
                id + " " + destination + " " + date + " " +
                hour + " " + airport + " " +  NoSeats +
                '}';
    }
}