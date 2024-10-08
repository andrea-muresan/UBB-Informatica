using System;
using System.Collections.Generic;
using System.Data;
using Model;
using Persistence;


namespace travelAgency.repository
{

    public class TicketDBRepository : ITicketRepository
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        IDictionary<String, string> props;
        
        public TicketDBRepository(IDictionary<String, string> props)
        {
            log.Info("Creating TicketDBRepository");
            this.props = props;
        }

        public IEnumerable<Ticket> FindAll()
        {
            throw new NotImplementedException();
        }

        public Ticket FindOne(int id)
        {
            throw new NotImplementedException();
        }

        public void Save(Ticket entity)
        {
            log.InfoFormat("Saving ticket values: {0}, {1}, {2}", entity.Flight.Id, entity.ClientName, entity.NoSeats);
            IDbConnection con = DBUtils.getConnection(props);
            IList<Flight> ticketsR = new List<Flight>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "INSERT INTO tickets(flight_id, client_name, client_address, tourists_names, no_seats) " +
                    "VALUES (@flightId, @client, @address, @tourists, @noSeats)";
                var paramFlightId = comm.CreateParameter();
                paramFlightId.ParameterName = "@flightId";
                paramFlightId.Value = entity.Flight.Id;
                comm.Parameters.Add(paramFlightId);
                
                var paramClient = comm.CreateParameter();
                paramClient.ParameterName = "@client";
                paramClient.Value = entity.ClientName;
                comm.Parameters.Add(paramClient);
                
                var paramAddress = comm.CreateParameter();
                paramAddress.ParameterName = "@address";
                paramAddress.Value = entity.ClientAddress;
                comm.Parameters.Add(paramAddress);
                
                var paramTourists = comm.CreateParameter();
                paramTourists.ParameterName = "@tourists";
                paramTourists.Value = entity.ClientsNames;
                comm.Parameters.Add(paramTourists);
                
                var paramNoSeats = comm.CreateParameter();
                paramNoSeats.ParameterName = "@noSeats";
                paramNoSeats.Value = entity.NoSeats;
                comm.Parameters.Add(paramNoSeats);

                int noVal = comm.ExecuteNonQuery();
                log.InfoFormat("{0} flights saved", noVal);
            }
        }

        public void Delete(int id)
        {
            throw new NotImplementedException();
        }

        public void Update(Ticket entity)
        {
            throw new NotImplementedException();
        }
    }
}