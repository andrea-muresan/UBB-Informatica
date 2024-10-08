using System;
using System.Collections.Generic;
using Model;
using Model.dto;

namespace Networking
{
    public class ObjectResponseProtocol
    {
        public interface Response
        {
        }

        [Serializable]
        public class OkResponse : Response
        {

        }

        [Serializable]
        public class ErrorResponse : Response
        {
            private string message;

            public ErrorResponse(string message)
            {
                this.message = message;
            }

            public virtual string Message
            {
                get { return message; }
            }
        }
        
        [Serializable]
        public class GetFlightsResponse : Response
        {
            public IEnumerable<Flight> flights { get; }
            public GetFlightsResponse(IEnumerable<Flight> flights)
            {
                this.flights = flights;
            }
        }
        
        [Serializable]
        public class GetFlightsDateDestResponse : Response
        {
            public IEnumerable<Flight> flights { get; }
            public GetFlightsDateDestResponse(IEnumerable<Flight> flights)
            {
                this.flights = flights;
            }
        }

        [Serializable]
        public class UpdateResponse : Response
        {
            public Flight flight { get; }
            public UpdateResponse(Flight flight)
            {
                this.flight = flight;
            }
        }

    }
}