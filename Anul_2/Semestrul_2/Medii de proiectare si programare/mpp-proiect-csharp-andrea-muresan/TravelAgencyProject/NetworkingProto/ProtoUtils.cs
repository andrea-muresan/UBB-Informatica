using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model.dto;

namespace NetworkingProto
{
    public class ProtoUtils
    {
        public static Model.dto.UserDto getUserDto(Request request)
        {
            Model.dto.UserDto userDto = new Model.dto.UserDto(request.User.Username, request.User.Password);
            return userDto;
        }
        
        public static Response createOkResponse()
        {
            Response response = new Response { Type = Response.Types.Type.Ok };
            return response;
        }
        
        public static Response createErrorResponse(string message)
        {
            Response response = new Response { Type = Response.Types.Type.Error, Error = message };
            return response;
        }
        
        public static Model.dto.TicketPurchaseDto getTicketPurchaseDto(Request request)
        {
            Model.dto.TicketPurchaseDto tpDto = new Model.dto.TicketPurchaseDto(request.Ticket.Flight.Id, request.Ticket.ClientName, request.Ticket.ClientAddress, request.Ticket.TouristsNames, request.Ticket.NoOfTickets.ToString());
            return tpDto;
        }
        
        public static Response createFindAllFlightsResponse(IEnumerable<Model.Flight> flights)
        {
            Response response = new Response { Type = Response.Types.Type.GetFlights };
            foreach (Model.Flight flight in flights)
            {
                var x = flight.Date;
                String date =  x.ToString("dd-MM-yyyy");;
                String time = x.ToString("HH:mm:ss");
                Flight fl = new Flight
                {
                    Id = flight.Id, Destination = flight.Destination, Date = date, Time = time,
                    Airport = flight.Airport, NoSeats = flight.NoSeats
                };
                
                response.Flights.Add(fl);
            }
            return response;
        }
        
        public static Model.dto.SearchFlightsDto getSearchFlightDto(Request request)
        {
            Model.dto.SearchFlightsDto searchFlightsDto = new Model.dto.SearchFlightsDto(request.SearchFlights.Destination, request.SearchFlights.Date);
            return searchFlightsDto;
        }
        
        public static Response createFindFlightsDestDateResponse(IEnumerable<Model.Flight> flights)
        {
            Response response = new Response { Type = Response.Types.Type.GetFlightsDestDate };
            foreach (Model.Flight flight in flights)
            {
                var x = flight.Date;
                String date =  x.ToString("dd-MM-yyyy");;
                String time = x.ToString("HH:mm:ss");
                Flight fl = new Flight
                {
                    Id = flight.Id, Destination = flight.Destination, Date = date, Time = time,
                    Airport = flight.Airport, NoSeats = flight.NoSeats
                };
                
                response.Flights.Add(fl);
            }
            return response;
        }
        
        
        
        public static Response createFlightResponse(Model.Flight flight)
        {
            var x = flight.Date;
            String date =  x.ToString("dd-MM-yyyy");;
            String time = x.ToString("HH:mm:ss");
            Flight fl = new Flight { Id = flight.Id, Destination = flight.Destination, Date = date, Time = time, Airport = flight.Airport, NoSeats = flight.NoSeats};
            Response response = new Response { Type = Response.Types.Type.Flight , Flight = fl};
            return response;
        }
    }
}