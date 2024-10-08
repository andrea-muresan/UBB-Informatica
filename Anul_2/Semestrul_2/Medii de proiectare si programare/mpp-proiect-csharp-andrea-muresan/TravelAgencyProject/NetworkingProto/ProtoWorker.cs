using Model;
using Model.dto;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Google.Protobuf;

namespace NetworkingProto
{
    public class ProtoWorker:IObserver
    {
        private IServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private volatile bool connected;

        public ProtoWorker(IServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                connected = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        public virtual void Run()
        {
            while (connected)
            {
                try
                {
                    Request request = Request.Parser.ParseDelimitedFrom(stream);
                    Response response = handleRequest(request);
                    if (response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;
            Request.Types.Type reqType = request.Type;

            switch (reqType)
            {
                case Request.Types.Type.Login:
                    {
                        Console.WriteLine("Log in request...");
                        Model.dto.UserDto userDto = ProtoUtils.getUserDto(request);
                        try
                        {
                            lock (server)
                            {
                                server.LogInVerify(userDto.Username, userDto.Password, this);
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch(MyException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case Request.Types.Type.Logout:
                    {
                        Console.WriteLine("Log out request...");
                        Model.User employee = DtoUtils.GetFromDTO(ProtoUtils.getUserDto(request));
                        try
                        {
                            lock (server)
                            {
                                server.logout(employee, this);
                            }
                            connected = false;
                            return ProtoUtils.createOkResponse();
                        }
                        catch (MyException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case Request.Types.Type.Purchase:
                {
                    Console.WriteLine("Purchase request...");
                    Model.dto.TicketPurchaseDto tpDto = ProtoUtils.getTicketPurchaseDto(request);
                    try
                    {
                        lock (server)
                        {
                            server.buyTicket(tpDto.FlightId, tpDto.ClientName, tpDto.ClientAddress, tpDto.ClientsNames, tpDto.NoSeats);
                        }
                        return ProtoUtils.createOkResponse();
                    }
                    catch (MyException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.GetFlights:
                {
                    Console.WriteLine("Get Flights request...");
                    try
                    {
                        IEnumerable<Model.Flight> flights;
                        lock (server)
                        {
                            flights = server.GetAllFlights();
                        }
                        return ProtoUtils.createFindAllFlightsResponse(flights);
                    }
                    catch (MyException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);
                    }
                }
                case Request.Types.Type.GetFlightsDestDate:
                {
                    Console.WriteLine("Get Flights Dest Date request...");
                    try
                    {
                        IEnumerable<Model.Flight> flights;
                        Model.dto.SearchFlightsDto flDto = ProtoUtils.getSearchFlightDto(request);
                        lock (server)
                        {
                            flights = server.GetFlightsByDateDestination(flDto.Destination, flDto.Date);
                        }
                        return ProtoUtils.createFindFlightsDestDateResponse(flights);
                    }
                    catch (MyException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);
                    }
                }
            }
            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }
        }
        

        public void UpdateFlight(Model.Flight flight)
        {
            Console.WriteLine("Notify users in worker, flights updates  " + flight);
            try
            {
                sendResponse(ProtoUtils.createFlightResponse(flight));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
    }
}