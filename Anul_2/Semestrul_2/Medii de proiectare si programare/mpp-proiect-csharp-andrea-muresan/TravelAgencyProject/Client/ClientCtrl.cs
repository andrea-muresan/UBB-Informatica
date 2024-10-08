using System;
using System.Collections.Generic;
using System.Windows.Forms;
using Model;
using Services;

namespace Client
{
    public class ClientCtrl : IObserver
    {
        public event EventHandler<FlightEventArgs> updateEvent; //ctrl calls it when it has received an update
        private readonly IServices server;
        private User currentUser;
        public ClientCtrl(IServices server)
        {
            this.server = server;
            currentUser = null;
        }

        public void Login(String username, String password)
        {
            server.LogInVerify(username, password, this);
            Console.WriteLine("Login succeeded ....");
            currentUser = new User(username, password);
            Console.WriteLine("Current user {0}", currentUser);
        }

        public IEnumerable<Flight> GetAllFlights()
        {
            IEnumerable<Flight> flights = server.GetAllFlights();
            return flights;
        }

        public IEnumerable<Flight> GetFlightsByDateDestination(string destination, string date)
        {
            return server.GetFlightsByDateDestination(destination, date);
        }

        public void BuyTicket(int flightId,string clientName,string clientAddress,string touristsNames,string noSeats)
        {
            server.buyTicket(flightId, clientName, clientAddress, touristsNames, noSeats);
            // FlightEventArgs flArgs = new FlightEventArgs(FlightEvent.Purchase, flightId);
            // OnFlightEvent(flArgs);
            
        }
        

        // public void logout()
        // {
        //     Console.WriteLine("Ctrl logout");
        //     server.logout(currentUser, this);
        //     currentUser = null;
        // }

        protected virtual void OnFlightEvent(FlightEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
        

        public void UpdateFlight(Flight flight)
        {
            Console.WriteLine("Flight update "+flight);
            FlightEventArgs flightArgs = new FlightEventArgs(FlightEvent.Purchase, flight);
            OnFlightEvent(flightArgs);
            
        }

        public void Logout()
        {
            Console.WriteLine("Ctrl logout");
            server.logout(currentUser, this);
            currentUser = null;
        }
    }
}