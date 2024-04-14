package app.services;


import app.model.Flight;

public interface IObserver {
    void updateFlight(Flight flight);
}