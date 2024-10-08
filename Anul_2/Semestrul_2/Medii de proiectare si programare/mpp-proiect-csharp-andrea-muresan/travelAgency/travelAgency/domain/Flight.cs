using System;

namespace travelAgency.domain
{
    public class Flight : Entity<int>
    {
        public string Destination { get; set; }
        public DateTime Date { get; set; }
        public string Airport { get; set; }
        public int NoSeats { get; set; }

        public Flight(string destination, DateTime date, string airport, int noSeats)
        {
            Destination = destination;
            this.Date = date;
            Airport = airport;
            NoSeats = noSeats;
        }
        public Flight(){}

        public Flight(Flight flight)
        {
            this.Destination = flight.Destination;
            this.Date = flight.Date;
            this.Airport = flight.Airport;
            this.NoSeats = flight.NoSeats;
            this.Id = flight.Id;
        }

        public override string ToString()
        {
            return Destination + Date;
        }
    }
}