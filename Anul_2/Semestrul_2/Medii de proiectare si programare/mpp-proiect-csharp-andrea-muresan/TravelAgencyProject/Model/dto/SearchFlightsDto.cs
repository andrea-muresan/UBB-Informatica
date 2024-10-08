using System;
using System.Collections.Generic;

namespace Model.dto
{
    [Serializable]
    public class SearchFlightsDto
    {
        public string Destination { get;}
        public string Date { get; }

        public SearchFlightsDto(string destination, string date)
        {
            Destination = destination;
            Date = date;
        }
        
        public override bool Equals(object obj)
        {
            return obj is SearchFlightsDto dto &&
                   Date == dto.Date &&
                   Destination == dto.Destination;
        }

        public override int GetHashCode()
        {
            int hashCode = 568732665;
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Destination);
            hashCode = hashCode * -1521134295 + EqualityComparer<string>.Default.GetHashCode(Date);
            return hashCode;
        }
    }
}