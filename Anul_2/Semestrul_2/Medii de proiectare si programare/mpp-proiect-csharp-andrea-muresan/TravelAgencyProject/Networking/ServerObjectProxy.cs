using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Model;
using Model.dto;
using Services;

namespace Networking
{
	public class ServerProxy : IServices
	{
		private string host;
		private int port;

		private IObserver client;

		private NetworkStream stream;

		private IFormatter formatter;
		private TcpClient connection;

		private Queue<ObjectResponseProtocol.Response> responses;
		private volatile bool finished;
		private EventWaitHandle _waitHandle;

		public ServerProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses = new Queue<ObjectResponseProtocol.Response>();
		}

		private void closeConnection()
		{
			finished = true;
			try
			{
				stream.Close();

				connection.Close();
				_waitHandle.Close();
				client = null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

		}

		private void sendRequest(Request request)
		{
			try
			{
				formatter.Serialize(stream, request);
				stream.Flush();
			}
			catch (Exception e)
			{
				throw new MyException("Error sending object " + e);
			}

		}

		private ObjectResponseProtocol.Response readResponse()
		{
			ObjectResponseProtocol.Response response = null;
			try
			{
				_waitHandle.WaitOne();
				lock (responses)
				{
					//Monitor.Wait(responses); 
					response = responses.Dequeue();

				}


			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}

			return response;
		}

		private void initializeConnection()
		{
			try
			{
				connection = new TcpClient(host, port);
				stream = connection.GetStream();
				formatter = new BinaryFormatter();
				finished = false;
				_waitHandle = new AutoResetEvent(false);
				startReader();
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		private void startReader()
		{
			Thread tw = new Thread(run);
			tw.Start();
		}


		private void handleUpdate(ObjectResponseProtocol.Response response)
		{
			if (response is ObjectResponseProtocol.UpdateResponse)
			{
				Console.WriteLine("handle update in proxy");
				ObjectResponseProtocol.UpdateResponse response1 = (ObjectResponseProtocol.UpdateResponse)response;
				client.UpdateFlight(response1.flight);
			}

		}

		public virtual void run()
		{
			while (!finished)
			{
				try
				{
					object response = formatter.Deserialize(stream);
					Console.WriteLine("response received " + response);
					if (response is ObjectResponseProtocol.UpdateResponse)
					{
						handleUpdate((ObjectResponseProtocol.UpdateResponse)response);
					}
					else
					{

						lock (responses)
						{


							responses.Enqueue((ObjectResponseProtocol.Response)response);

						}

						_waitHandle.Set();
					}
				}
				catch (Exception e)
				{
					Console.WriteLine("Reading error " + e);
				}

			}
		}

		public void LogInVerify(string username, string password, IObserver client)
		{
			initializeConnection();
			UserDto udto = DtoUtils.getDTO(new User(username, password));
			sendRequest(new LoginRequest(udto));
			ObjectResponseProtocol.Response response = readResponse();
			if (response is ObjectResponseProtocol.OkResponse)
			{
				this.client = client;
				return;
			}

			if (response is ObjectResponseProtocol.ErrorResponse)
			{
				ObjectResponseProtocol.ErrorResponse err = (ObjectResponseProtocol.ErrorResponse)response;
				closeConnection();
				throw new MyException(err.Message);
			}
		}

		public IEnumerable<Flight> GetAllFlights()
		{
			sendRequest(new GetFlightsRequest());

			ObjectResponseProtocol.Response response = readResponse();
			if (response is ObjectResponseProtocol.ErrorResponse)
			{
				ObjectResponseProtocol.ErrorResponse err = (ObjectResponseProtocol.ErrorResponse)response;
				throw new MyException(err.Message);
			}

			ObjectResponseProtocol.GetFlightsResponse response1 = (ObjectResponseProtocol.GetFlightsResponse)response;
			IEnumerable<Flight> flights = response1.flights;
			return flights;
		}

		public IEnumerable<Flight> GetFlightsByDateDestination(string destination, string date)
		{
			sendRequest(new GetFlightsDateDestRequest(new SearchFlightsDto(destination, date)));

			ObjectResponseProtocol.Response response = readResponse();
			if (response is ObjectResponseProtocol.ErrorResponse)
			{
				ObjectResponseProtocol.ErrorResponse err = (ObjectResponseProtocol.ErrorResponse)response;
				throw new MyException(err.Message);
			}

			ObjectResponseProtocol.GetFlightsDateDestResponse response1 =
				(ObjectResponseProtocol.GetFlightsDateDestResponse)response;
			IEnumerable<Flight> flights = response1.flights;
			return flights;
		}

		public void buyTicket(int flight_id, string name, string address, string tourists, string noSeats)
		{
			TicketPurchaseDto ticketPurchase = new TicketPurchaseDto(flight_id, name, address, tourists, noSeats);
			sendRequest(new PurchaseRequest(ticketPurchase));

			ObjectResponseProtocol.Response response = readResponse();
			if (response is ObjectResponseProtocol.OkResponse)
			{
				Console.WriteLine("Tickets successfully bought");
				return;
			}

			if (response is ObjectResponseProtocol.ErrorResponse)
			{
				ObjectResponseProtocol.ErrorResponse err = (ObjectResponseProtocol.ErrorResponse)response;
				closeConnection();
				throw new MyException(err.Message);
			}
		}

		public Flight FindFlight(int id)
		{
			throw new NotImplementedException();
		}

		public string SignUp(string username, string password)
		{
			throw new NotImplementedException();
		}

		public void logout(User currentUser, IObserver clientCtrl)
		{
			UserDto udto = DtoUtils.getDTO(currentUser);
			sendRequest(new LogoutRequest(udto));
			ObjectResponseProtocol.Response response = readResponse();
			closeConnection();
			if (response is ObjectResponseProtocol.ErrorResponse)
			{
				ObjectResponseProtocol.ErrorResponse err = (ObjectResponseProtocol.ErrorResponse)response;
				throw new MyException(err.Message);
			}
		}
	}
}