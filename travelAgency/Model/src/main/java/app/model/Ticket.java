package app.model;

public class Ticket extends Entity<Integer>{
    private Flight flight;
    private String clientName;
    private String clientAddress;
    private String touristsNames;

    private int noSeats;

    public Ticket(Flight flight, String clientName, String clientAddress, String touristsNames, int noSeats) {
        this.flight = flight;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.touristsNames = touristsNames;
        this.noSeats = noSeats;
    }

    public int getNoSeats() {
        return noSeats;
    }

    public void setNoSeats(int noSeats) {
        this.noSeats = noSeats;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getTouristsNames() {
        return touristsNames;
    }

    public void setTouristsNames(String touristsNames) {
        this.touristsNames = touristsNames;
    }
}
