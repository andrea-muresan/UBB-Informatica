using System;
using System.Collections.Generic;
using Model;

namespace Services
{
    public interface IServices
    {
        void LogInVerify(string username, string password, IObserver client);

        IEnumerable<Flight> GetAllFlights();

        IEnumerable<Flight> GetFlightsByDateDestination(string destination, string inputdate);

        void buyTicket(int flight_id, string name, string address, string tourists, string noSeats);

        Flight? FindFlight(int id);

        string SignUp(string username, string password);
        void logout(User currentUser, IObserver clientCtrl);
    }
}