using System;
using System.Collections.Generic;

namespace Model.dto
{
    [Serializable]
    public class TicketPurchaseDto
    {
        public int FlightId { get; set; }
        public string ClientName { get; set; }
        public string ClientAddress { get; set; }
        public string ClientsNames { get; set; }
        public string NoSeats { get; set; }

        public TicketPurchaseDto(int flightId, string clientName, string clientAddress, string clientsNames, string noSeats)
        {
            FlightId = flightId;
            ClientName = clientName;
            ClientAddress = clientAddress;
            ClientsNames = clientsNames;
            NoSeats = noSeats;
        }

        public override bool Equals(object obj)
        {
            return obj is TicketPurchaseDto dto &&
                   FlightId == dto.FlightId &&
                   ClientName == dto.ClientName &&
                   ClientAddress == dto.ClientAddress &&
                   ClientsNames == dto.ClientsNames &&
                   NoSeats == dto.NoSeats;
        }

        public override int GetHashCode()
        {
            int hashCode = 568732665;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(ClientName);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(ClientAddress);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(ClientsNames);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(NoSeats);
            return hashCode;
        }
    }
}