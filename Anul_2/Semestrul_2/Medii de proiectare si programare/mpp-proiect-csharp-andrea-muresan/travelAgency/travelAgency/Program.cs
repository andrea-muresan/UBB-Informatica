using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using travelAgency.domain;
using travelAgency.GUI;
using travelAgency.repository;
using travelAgency.service;

namespace travelAgency
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("travelAgency_DB"));
            IUserRepository userRepo = new UserDBRepository(props);
            IFlightRepository flightRepo = new FlightDBRepository(props);
            ITicketRepository ticketRepo = new TicketDBRepository(props);
            Service srv = new Service(userRepo, flightRepo, ticketRepo);
            
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(srv));
            
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
    
    
}