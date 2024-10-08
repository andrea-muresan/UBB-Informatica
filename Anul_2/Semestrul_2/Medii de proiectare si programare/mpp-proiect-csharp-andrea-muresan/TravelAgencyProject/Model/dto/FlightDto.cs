using System;
using System.Collections.Generic;

namespace Model.dto
{
    [Serializable]
    public class FlightDto
    {
        public string Destination { get; set; }
        public DateTime Date { get; set; }
        public string Airport { get; set; }
        public int NoSeats { get; set; }


        public FlightDto(string destination, DateTime date, string airport, int noSeats)
        {
            Destination = destination;
            Date = date;
            Airport = airport;
            NoSeats = noSeats;
        }
        
        public override bool Equals(object obj)
        {
            return obj is FlightDto dto &&
                   Destination == dto.Destination &&
                   Date == dto.Date &&
                   Airport == dto.Airport &&
                   NoSeats == dto.NoSeats;
        }

        public override int GetHashCode()
        {
            int hashCode = 568732665;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Destination);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Airport);
            return hashCode;
        }
    }
    
    
}