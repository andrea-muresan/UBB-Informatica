using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Net.Sockets;
using System.Threading;
using Networking;
using NetworkingProto;
using Persistence;
using ServerTemplate;
using Services;
using travelAgency.repository;

namespace Server;

public class StartServer
{
    private static int DEFAULT_PORT=55556;
        private static String DEFAULT_IP="127.0.0.1";
        static void Main(string[] args)
        {
            
           // IUserRepository userRepo = new UserRepositoryMock();
           Console.WriteLine("Reading properties from app.config ...");
           int port = DEFAULT_PORT;
           String ip = DEFAULT_IP;
           String portS= ConfigurationManager.AppSettings["port"];
           if (portS == null)
           {
               Console.WriteLine("Port property not set. Using default value "+DEFAULT_PORT);
           }
           else
           {
               bool result = Int32.TryParse(portS, out port);
               if (!result)
               {
                   Console.WriteLine("Port property not a number. Using default value "+DEFAULT_PORT);
                   port = DEFAULT_PORT;
                   Console.WriteLine("Portul "+port);
               }
           }
           String ipS=ConfigurationManager.AppSettings["ip"];
           
           if (ipS == null)
           {
               Console.WriteLine("Port property not set. Using default value "+DEFAULT_IP);
           }
           Console.WriteLine("Configuration Settings for database {0}",GetConnectionStringByName("travelAgencyDB"));
           IDictionary<String, string> props = new SortedList<String, String>();
           props.Add("ConnectionString", GetConnectionStringByName("travelAgencyDB"));
           IUserRepository userRepo=new UserDBRepository(props);
           IFlightRepository flightRepo=new FlightDBRepository(props);
           ITicketRepository ticketRepo=new TicketDBRepository(props);
           // IUserRepository userRepo=new UserRepositoryMock();
           // IMessageRepository messageRepository=new MessageRepositoryMock();
           IServices serviceImpl = new ServerImpl(userRepo, flightRepo, ticketRepo);

         
           Console.WriteLine("Starting server on IP {0} and port {1}", ip, port);
           SerialServer server = new SerialServer(ip,port, serviceImpl);
            // ProtoV3Server server = new ProtoV3Server(ip, port, serviceImpl);
            
            server.Start();
            Console.WriteLine("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();
            
        }
        
        public static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
            {
                string relativePath = settings.ConnectionString;
                
                // Combine the project directory with the relative path to get the full path to the database file
                string workingDirectory = Environment.CurrentDirectory; // current directory
                string projectDirectory = Directory.GetParent(workingDirectory).Parent.Parent.FullName;
               
                returnValue = Path.Combine(projectDirectory,  relativePath);
                returnValue = "URI=file:" + returnValue.Replace("\\", "/");
            }
            
            return returnValue;
        }
}

    public class SerialServer: ConcurrentServer 
    {
        private IServices server;
        private ClientWorker worker;
        public SerialServer(string host, int port, IServices server) : base(host, port)
            {
                this.server = server;
                Console.WriteLine("SerialServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

    public class ProtoV3Server : ConcurrentServer
    {
        private IServices server;
        private ProtoWorker worker;

        public ProtoV3Server(string host, int port, IServices server)
            :base(host, port)
        {
            this.server = server;
            Console.WriteLine("Proto Server...");
        }

        protected override Thread createWorker(TcpClient client)
        {
            worker = new ProtoWorker(server, client);
            return new Thread(new ThreadStart(worker.Run));
        }
    }


