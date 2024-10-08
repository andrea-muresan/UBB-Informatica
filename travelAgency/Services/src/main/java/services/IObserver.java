package services;


import app.model.Flight;

import java.io.IOException;

public interface IObserver {
    void updateFlight(Flight flight) throws IOException;
}