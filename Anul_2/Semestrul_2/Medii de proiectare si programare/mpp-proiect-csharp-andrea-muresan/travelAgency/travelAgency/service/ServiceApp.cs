using System;
using System.Collections.Generic;
using System.Web.Management;
using travelAgency.domain;
using travelAgency.repository;

namespace travelAgency.service;

public class ServiceApp
{
    private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    private FlightDBRepository _flightRepo;
    private TicketDBRepository _ticketRepo;

    public ServiceApp(FlightDBRepository flightRepo, TicketDBRepository ticketRepo)
    {
        log.InfoFormat("Creating ServiceApp");
        _flightRepo = flightRepo;
        _ticketRepo = ticketRepo;
    }

    public IEnumerable<Flight> GetAllFlights()
    {
        return _flightRepo.FindAll();
    }
    
    public IEnumerable<Flight> GetFlightsByDateDestination(string destination, string inputdate)
    {
        DateTime date = DateTime.ParseExact(inputdate, "dd/MM/yyyy", null);
        string outputDate = date.ToString("yyyy-MM-dd");
        return _flightRepo.FindByDestinationDate(destination, outputDate);
    }

    public string buyTicket(int flight_id, string name, string address, string tourists, string noSeats) 
    {
        try
        {
            Int32 noSeatsInt= Int32.Parse(noSeats);

            if (tourists.Split(';').Length + 1 < noSeatsInt)
            {
                return "There are not enough seats for all the tourists";
            }
            Flight flight = _flightRepo.FindOne(flight_id);
            if (flight != null)
            {
                if (flight.NoSeats < noSeatsInt)
                {
                    return "The plane does not have enough seats";
                }
                Ticket t = new Ticket(flight, name, address,tourists, noSeatsInt);
                _ticketRepo.Save(t);
                
                flight.NoSeats = flight.NoSeats - noSeatsInt;
                _flightRepo.Update(flight);
            }
            
            return "Tickets bought";
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return e.Message;
        }
        
    }

    public Flight? FindFlight(int id)
    {
        return _flightRepo.FindOne(id);
    }
}