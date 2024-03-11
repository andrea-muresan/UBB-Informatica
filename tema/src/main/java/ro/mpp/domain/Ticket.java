package ro.mpp.domain;

public class Ticket extends Entity<Long>{
    private int flightId;
    private String clientName;
    private String clientAddress;
    private String touristsNames;

    public Ticket(int flightId, String clientName, String clientAddress, String touristsNames) {
        this.flightId = flightId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
        this.touristsNames = touristsNames;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
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
