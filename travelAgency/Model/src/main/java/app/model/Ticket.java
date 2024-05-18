package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;

@jakarta.persistence.Entity
@Table( name = "tickets" )
public class Ticket extends Entity<Integer>{
    @Column(name = "flight_id")
    private Integer flight_id;
    @Column(name = "client_name")
    private String client_name;
    @Column(name = "client_address")
    private String client_address;
    @Column(name = "tourists_names")
    private String tourists_names;
    @Column(name = "no_seats")
    private Integer no_seats;

    public Ticket() {
        id=0;
        no_seats =1;
        client_name =client_address= tourists_names ="default";
    }

    public Ticket(Integer flight_id, String client_name, String clientAddress, String tourists_names, int no_seats) {
        this.flight_id = flight_id;
        this.client_name = client_name;
        this.client_address = clientAddress;
        this.tourists_names = tourists_names;
        this.no_seats = no_seats;
    }

    @Override
    @Id
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo_seats() {
        return no_seats;
    }

    public void setNo_seats(Integer no_seats) {
        this.no_seats = no_seats;
    }

    public Integer getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(Integer flight_id) {
        this.flight_id = flight_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClientAddress() {
        return client_address;
    }

    public void setClientAddress(String clientAddress) {
        this.client_address = clientAddress;
    }

    public String getTourists_names() {
        return tourists_names;
    }

    public void setTourists_names(String tourists_names) {
        this.tourists_names = tourists_names;
    }
}
