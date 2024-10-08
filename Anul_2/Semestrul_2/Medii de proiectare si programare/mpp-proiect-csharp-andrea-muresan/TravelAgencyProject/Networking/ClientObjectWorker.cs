using System;
using System.Collections.Generic;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using Model;
using Model.dto;
using Services;

namespace Networking
{
    public class ClientWorker : IObserver
    {
        private IServices server;
		private TcpClient connection;

		private NetworkStream stream;
		private IFormatter formatter;
		private volatile bool connected;
		public ClientWorker(IServices server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{
				
				stream=connection.GetStream();
                formatter = new BinaryFormatter();
				connected=true;
			}
			catch (Exception e)
			{
                Console.WriteLine(e.StackTrace);
			}
		}

		public virtual void run()
		{
			while(connected)
			{
				try
				{
                    object request = formatter.Deserialize(stream);
					object response =handleRequest((Request)request);
					if (response!=null)
					{
					   sendResponse((ObjectResponseProtocol.Response) response);
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
				Console.WriteLine("Error "+e);
			}
		}
		
		private ObjectResponseProtocol.Response handleRequest(Request request)
		{
			ObjectResponseProtocol.Response response =null;
			if (request is LoginRequest)
			{
				Console.WriteLine("Login request ...");
				LoginRequest logReq =(LoginRequest)request;
				UserDto udto =logReq.User;
				User user =DtoUtils.GetFromDTO(udto);
				try
                {
                    lock (server)
                    {
                        server.LogInVerify(user.Username, user.Password, this);
                    }
					return new ObjectResponseProtocol.OkResponse();
				}
				catch (MyException e)
				{
					connected=false;
					return new ObjectResponseProtocol.ErrorResponse(e.Message);
				}
			}
		
			if (request is GetFlightsRequest)
			{
				Console.WriteLine("Get all flights request...");
				try
				{
					IEnumerable<Flight> flights;
					lock (server)
					{
						flights = server.GetAllFlights();
					}
					return new ObjectResponseProtocol.GetFlightsResponse(flights);
				}
				catch (MyException e)
				{
					return new ObjectResponseProtocol.ErrorResponse(e.Message);
				}
			}
			
			if (request is GetFlightsDateDestRequest)
			{
				Console.WriteLine("Get flights by date and dest request...");
				GetFlightsDateDestRequest reqReq =(GetFlightsDateDestRequest)request;
				SearchFlightsDto flDto = reqReq.searchFlightDetails;
				// User user =DtoUtils.GetFromDTO(udto);
				try
				{
					IEnumerable<Flight> flights;
					lock (server)
					{
						flights = server.GetFlightsByDateDestination(flDto.Destination, flDto.Date);
					}
					return new ObjectResponseProtocol.GetFlightsDateDestResponse(flights);
				}
				catch (MyException e)
				{
					return new ObjectResponseProtocol.ErrorResponse(e.Message);
				}
			}
			
			if (request is PurchaseRequest)
			{
				Console.WriteLine("Purchase request...");
				try
				{
					PurchaseRequest purchaseRequest = (PurchaseRequest) request;
					TicketPurchaseDto tpDto = purchaseRequest.ticketPurchase;
					lock (server)
					{
						server.buyTicket(tpDto.FlightId, tpDto.ClientName, tpDto.ClientAddress, tpDto.ClientsNames, tpDto.NoSeats);
					}
					return new ObjectResponseProtocol.OkResponse();
				}
				catch(MyException e)
				{
					return new ObjectResponseProtocol.ErrorResponse(e.Message);
				}
			}
			
			if (request is LogoutRequest)
			{
				Console.WriteLine("Logout request");
				LogoutRequest logReq =(LogoutRequest)request;
				UserDto udto =logReq.User;
				User user =DtoUtils.GetFromDTO(udto);
				try
				{
					lock (server)
					{

						server.logout(user, this);
					}
					connected=false;
					return new ObjectResponseProtocol.OkResponse();

				}
				catch (MyException e)
				{
					return new ObjectResponseProtocol.ErrorResponse(e.Message);
				}
			}
			
			return response;
		}

	private void sendResponse(ObjectResponseProtocol.Response response)
		{
			Console.WriteLine("sending response "+response);
			lock (stream)
			{
				formatter.Serialize(stream, response);
				stream.Flush();
			}

		}

		public void UpdateFlight(Flight flight)
		{
			Console.WriteLine("Notify clients in worker " + flight);
			try
			{
				sendResponse(new ObjectResponseProtocol.UpdateResponse(flight));
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}
    }
    
}