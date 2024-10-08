using System;

namespace Model
{
    [Serializable]
    public class Ticket : Entity<int>
    {
        public Flight Flight { get; set; }
        public string ClientName { get; set; }
        public string ClientAddress { get; set; }
        public string ClientsNames { get; set; }
        public int NoSeats { get; set; }

        public Ticket(Flight flight, string clientName, string clientAddress, string clientsNames, int noSeats)
        {
            this.Flight = flight;
            this.ClientName = clientName;
            this.ClientAddress = clientAddress;
            this.ClientsNames = clientsNames;
            this.NoSeats = noSeats;
        }
    }
}