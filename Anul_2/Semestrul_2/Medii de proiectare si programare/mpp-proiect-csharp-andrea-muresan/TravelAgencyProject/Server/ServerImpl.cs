using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Web.Hosting;
using Model;
using Persistence;
using Services;

namespace Server
{
    public class ServerImpl : IServices
    {
        private static readonly log4net.ILog log =
            log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private IUserRepository _userRepo;
        private IFlightRepository _flightRepo;
        private ITicketRepository _ticketRepo;

        private readonly IDictionary<Int32, IObserver> loggedClients;

        // private Encrypt _encrypt = new Encrypt();

        public ServerImpl(IUserRepository userRepo, IFlightRepository flightRepo, ITicketRepository ticketRepo)
        {
            log.InfoFormat("Creating Service");
            _userRepo = userRepo;
            _flightRepo = flightRepo;
            _ticketRepo = ticketRepo;
            loggedClients = new Dictionary<Int32, IObserver>();
        }

        public ServerImpl(ServerImpl srv)
        {
            log.InfoFormat("Creating Service");
            _userRepo = srv._userRepo;
            _flightRepo = srv._flightRepo;
            _ticketRepo = srv._ticketRepo;
        }


        public void LogInVerify(string username, string password, IObserver client)
        {
            log.InfoFormat("Entering LogInVerify with values: username={0}, password=###", username);

            Model.User user = _userRepo.FindByUsername(username);
            if (user == null)
            {
                log.InfoFormat("Username={0} does not exist", username);
                throw new MyException("User does not exist.");
            }

            if (loggedClients.ContainsKey(user.Id))
            {
                log.InfoFormat("Username={0} already connected", username);
                throw new MyException("User already connected.");
            }

            if (user.Password == password)
            {
                log.InfoFormat("username={0} and password=### are correct", username);
                loggedClients.Add(user.Id, client);
            }
            else
            {
                log.InfoFormat("username={0} found - password incorrect", username);
                throw new MyException("Incorrect password");
            }


        }

        public IEnumerable<Model.Flight> GetAllFlights()
        {
            return _flightRepo.FindAll();
        }

        public IEnumerable<Model.Flight> GetFlightsByDateDestination(string destination, string inputdate)
        {
            DateTime date = DateTime.ParseExact(inputdate, "dd/MM/yyyy", null);
            string outputDate = date.ToString("yyyy-MM-dd");
            return _flightRepo.FindByDestinationDate(destination, outputDate);
        }

        public void buyTicket(int flight_id, string name, string address, string tourists, string noSeats)
        {
            try
            {
                Int32 noSeatsInt = Int32.Parse(noSeats);

                if (tourists != "" && tourists.Split(';').Length >= noSeatsInt) {
                    throw new MyException("Too many passengers for " + noSeatsInt + " seats");

                }

                Model.Flight flight = _flightRepo.FindOne(flight_id);
                if (flight != null)
                {
                    if (flight.NoSeats < noSeatsInt)
                    {
                        throw new MyException("The plane does not have enough seats");
                    }
                    
                    if (noSeatsInt < 1) {
                        throw new MyException("Enter at list one seat");
                    }

                    Ticket t = new Ticket(flight, name, address, tourists, noSeatsInt);
                    _ticketRepo.Save(t);

                    flight.NoSeats = flight.NoSeats - noSeatsInt;
                    _flightRepo.Update(flight);
                    notifyFlight(flight);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
            
        }

        public Model.Flight? FindFlight(int id)
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
                _userRepo.Save(new Model.User(username, password));
                return "Account created. Go back to Log In";
            }
            else return "Username or password are not long enough";
        }

        public void logout(Model.User currentUser, IObserver clientCtrl)
        {
            Model.User user = _userRepo.FindByUsername(currentUser.Username);
            IObserver localClient=loggedClients[user.Id];
            if (localClient==null)
                throw new MyException("User "+user.Id+" is not logged in.");
            loggedClients.Remove(user.Id);
        }

        private void notifyFlight(Model.Flight flight)
        {
            Console.WriteLine("notify flight updated");
            foreach (var elem in loggedClients)
            {
                Console.WriteLine("notify client {0}", elem.Key);
                Task.Run(() => elem.Value.UpdateFlight(flight));
            }
        }

    }

}