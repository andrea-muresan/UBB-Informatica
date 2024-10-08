using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms.VisualStyles;
using travelAgency.domain;

namespace travelAgency.repository
{

    public class FlightDBRepository : IFlightRepository
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        IDictionary<String, string> props;
        
        public FlightDBRepository(IDictionary<String, string> props)
        {
            log.Info("Creating FlightDBRepository");
            this.props = props;
        }
        
        public IEnumerable<Flight> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Flight> flightsR = new List<Flight>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT id, destination, date, time, airport, no_seats FROM flights";
            
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String dest = dataR.GetString(1);
                        DateTime date = DateTime.Parse(dataR.GetString(2) + " " + dataR.GetString(3));
                        String airport = dataR.GetString(4);
                        int noSeats = dataR.GetInt32(5);
                        Flight flight = new Flight(dest, date, airport, noSeats);
                        
                        flightsR.Add(flight);
                    }
                }
            }
            
            return flightsR;
        }

        public Flight? FindOne(int id)
        {
            IDbConnection con = DBUtils.getConnection(props);
            Flight flight = null;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT destination, date, time, airport, no_seats FROM flights where id=@id";
            
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        String dest = dataR.GetString(0);
                        DateTime date = DateTime.Parse(dataR.GetString(1) + " " + dataR.GetString(2));
                        String airport = dataR.GetString(3);
                        int noSeats = dataR.GetInt32(4);
                        flight = new Flight(dest, date, airport, noSeats);
                        flight.Id = id;
                    }
                }
            }

            return flight;
        }

        public void Save(Flight entity)
        {
            throw new NotImplementedException();
        }

        public void Delete(int id)
        {
            throw new NotImplementedException();
        }

        public void Update(Flight entity)
        {
            
            Console.WriteLine(entity.Date.Date);
            log.InfoFormat("Entering findByDestinationDate with values: id={0}, destination={1}, date={2}, airport={3}, noSeats={4}", entity.Id, entity.Destination, entity.Date, entity.Airport, entity.NoSeats);
            
            IDbConnection con = DBUtils.getConnection(props);
            List<Flight> flightsR = new List<Flight>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "UPDATE flights SET destination=@dest, date=@date, time=@time, airport=@airport, no_seats=@noSeats where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.Id;
                comm.Parameters.Add(paramId);

                var paramDate = comm.CreateParameter();
                paramDate.ParameterName = "@date";
                paramDate.Value = entity.Date.Date.ToString("yyyy-MM-dd");
                comm.Parameters.Add(paramDate);

                var paramDest = comm.CreateParameter();
                paramDest.ParameterName = "@dest";
                paramDest.Value = entity.Destination;
                comm.Parameters.Add(paramDest);
                
                var paramAirport = comm.CreateParameter();
                paramAirport.ParameterName = "@airport";
                paramAirport.Value = entity.Airport;
                comm.Parameters.Add(paramAirport);

                var paramTime = comm.CreateParameter();
                paramTime.ParameterName = "@time";
                paramTime.Value = entity.Date.TimeOfDay.ToString();
                comm.Parameters.Add(paramTime);

                var paramNoSeats = comm.CreateParameter();
                paramNoSeats.ParameterName = "@noSeats";
                paramNoSeats.Value = entity.NoSeats;
                comm.Parameters.Add(paramNoSeats);

                int noVal = comm.ExecuteNonQuery();

                log.InfoFormat("Exiting with {0} updated", noVal);
            }
        }

        public List<Flight> FindByDestinationDate(string destination, string date)
        {
            log.InfoFormat("Entering findByDestinationDate with values: destination={0}, date={1}", destination, date);
            
            IDbConnection con = DBUtils.getConnection(props);
            List<Flight> flightsR = new List<Flight>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT id, destination, date, time, airport, no_seats FROM flights WHERE date LIKE @date and destination LIKE @dest";
                var paramDate = comm.CreateParameter();
                paramDate.ParameterName = "@date";
                paramDate.Value = date;
                comm.Parameters.Add(paramDate);
                
                var paramDest = comm.CreateParameter();
                paramDest.ParameterName = "@dest";
                paramDest.Value = destination;
                comm.Parameters.Add(paramDest);
                
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idV = dataR.GetInt32(0);
                        String dest = dataR.GetString(1);
                        DateTime dateV = DateTime.Parse(dataR.GetString(2) + " " + dataR.GetString(3));
                        String airport = dataR.GetString(4);
                        int noSeats = dataR.GetInt32(5);
                        Flight flight = new Flight(dest, dateV, airport, noSeats);
                        flight.Id = idV;
                        
                        flightsR.Add(flight);
                    }
                }
            }
            
            log.InfoFormat("Exiting findByDestinationDate with {0} flights", flightsR.Count);
            return flightsR;
        }
    }
}