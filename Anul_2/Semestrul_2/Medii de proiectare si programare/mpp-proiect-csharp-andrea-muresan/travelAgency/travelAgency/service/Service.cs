using System;
using System.Collections.Generic;
using System.Windows.Forms;
using travelAgency.domain;
using travelAgency.repository;
using travelAgency.utils;

namespace travelAgency.service;

public class Service
{
    private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

    private IUserRepository _userRepo;
    private IFlightRepository _flightRepo;
    private ITicketRepository _ticketRepo;
    
    private Encrypt _encrypt = new Encrypt();

    public Service(IUserRepository userRepo, IFlightRepository flightRepo, ITicketRepository ticketRepo)
    {
        log.InfoFormat("Creating Service");
        _userRepo = userRepo;
        _flightRepo = flightRepo;
        _ticketRepo = ticketRepo;
    }

    public Service(Service srv)
    {
        log.InfoFormat("Creating Service");
        _userRepo = srv._userRepo;
        _flightRepo = srv._flightRepo;
        _ticketRepo = srv._ticketRepo;
    }

    public bool LogInVerify(string username, string password)
    {
        log.InfoFormat("Entering LogInVerify with values: username={0}, password=###", username);
        
        User user = _userRepo.FindByUsername(username);
        if (user == null)
        {
            log.InfoFormat("Username={0} does not exist", username);
            MessageBox.Show(@"Username does not exist");
            return false;
        } 
        if (user.Password == _encrypt.EncodePasswordToBase64(password))
        {
            log.InfoFormat("username={0} and password=### are correct", username);
            return true;
        }
        log.InfoFormat("username={0} found - password incorrect", username);
        MessageBox.Show(@"Wrong password");
        return false;
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

    public string SignUp(string username, string password)
    {
        if (_userRepo.FindByUsername(username) != null)
        {
            return "Unavailable username";
        }
        else if (username.Length > 3 && password.Length > 3)
        {
            _userRepo.Save(new User(username, password));
            return "Account created. Go back to Log In";
        }
        else return "Username or password are not long enough";
    }
    
}