using System;
using System.Collections.Generic;
using System.Data;
using System.Windows.Forms;
using Model;
using Services;


namespace Client.GUI
{

    public partial class App : Form
    {
        // private IServices service;
        private ClientCtrl ctrl;

        private DataTable flightsViewTable = new DataTable();

        public App(ClientCtrl ctrl)
        {
            this.ctrl = ctrl;

            InitializeComponent();

            // Configure DataGridViews
            // Controls.Add(flightsView);
            ctrl.updateEvent += updateFlight;
            DisplayFlights();

        }

        private void DisplayFlights()
        {
            // Call the function to get a list of flights
            IEnumerable<Flight> flights = ctrl.GetAllFlights();
            
            // Create a DataTable to hold the flights data
            DataTable dataTable = new DataTable();
            
            // Add columns to the DataTable
            dataTable.Columns.Add("Destination", typeof(string));
            dataTable.Columns.Add("Date-Time", typeof(DateTime));
            dataTable.Columns.Add("Airport", typeof(string));
            dataTable.Columns.Add("No seats", typeof(Int32));
            
            // Populate the DataTable with flights data
            foreach (Flight flight in flights)
            {
                if (flight.NoSeats != 0)
                {
                    DataRow row = dataTable.NewRow();
                    row["Destination"] = flight.Destination;
                    row["Date-Time"] = flight.Date;
                    row["Airport"] = flight.Airport;
                    row["No seats"] = flight.NoSeats;
                    dataTable.Rows.Add(row);
                }
            }
            
            
            
            // Bind the DataTable to the DataGridView
            flightsView.DataSource = dataTable;
        }

        private void DisplayFlightsByDateDestination(string destination, string date)
        {
            // Call the function to get a list of flights
            IEnumerable<Flight> flights = ctrl.GetFlightsByDateDestination(destination, date);
            
            // Create a DataTable to hold the flights data
            DataTable dataTable = new DataTable();
            
            // Add columns to the DataTable
            dataTable.Columns.Add("ID", typeof(Int32));
            dataTable.Columns.Add("Destination", typeof(string));
            dataTable.Columns.Add("Time", typeof(TimeSpan));
            dataTable.Columns.Add("Airport", typeof(string));
            dataTable.Columns.Add("No seats", typeof(Int32));
            
            // Populate the DataTable with flights data
            foreach (Flight flight in flights)
            {
                if (flight.NoSeats != 0)
                {
                    DataRow row = dataTable.NewRow();
                    row["ID"] = flight.Id;
                    row["Destination"] = flight.Destination;
                    row["Time"] = flight.Date.TimeOfDay;
                    row["Airport"] = flight.Airport;
                    row["No seats"] = flight.NoSeats;
                    dataTable.Rows.Add(row);
                }
            }
            
            // Bind the DataTable to the DataGridView
            searchView.DataSource = dataTable;
        }

        private void searchBtn_Click(object sender, EventArgs e)
        {
            string destination = destinationTextBox.Text;
            string date = dateTimePicker1.Text;

            DisplayFlightsByDateDestination(destination, date);
        }

        private void buyBtn_Click(object sender, EventArgs e)
        {
            if (searchView.SelectedCells.Count == 1)
            {
                DataGridViewRow row = searchView.Rows[searchView.SelectedCells[0].RowIndex];
            
                string clientName = this.clientNameTextBox.Text;
                string clientAddress = this.addresTextBox.Text;
                string touristsNames = this.touristsTextBox.Text;
                string noSeats = this.noSeatsTextBox.Text;
            
                int flightId = Int32.Parse(row.Cells["ID"].Value.ToString());
                // Flight flight = ctrl.FindFlight(flightId);
                try
                {
                    ctrl.BuyTicket(flightId, clientName, clientAddress, touristsNames, noSeats);
                    MessageBox.Show("Tickets are bought");
                }
                catch (Exception exception)
                {
                    MessageBox.Show(exception.Message);
                }
                
                
                // DisplayFlights();
                // if (flight != null)
                //     DisplayFlightsByDateDestination(flight.Destination, flight.Date.ToString("dd/MM/yyyy"));
                
            }
        }

        private void logOutBtn_Click(object sender, EventArgs e)
        {
            
            Console.WriteLine("AppWindow closing "+e.ToString());
            // if (e.CloseReason == CloseReason.UserClosing)
            // {
                ctrl.Logout();
                ctrl.updateEvent -= updateFlight;
                Application.Exit();
            // }
        }

        public void updateFlight(object sender, FlightEventArgs e)
        {
            if (e.FlightEventType==FlightEvent.Purchase)
            {
                Flight flight = (Flight) e.Data;
                Console.WriteLine("[AppWindow] flightUpdate "+ flight);
                flightsView.BeginInvoke(new UpdateDataGridViewCallback(this.DisplayFlights));

                int ok = 0;
                foreach (DataGridViewRow row in searchView.Rows)
                {
                    if (Int32.Parse(row.Cells["ID"].Value.ToString()) == flight.Id)
                    {
                        ok = 1;
                        break;
                    }
                }

                if (ok == 1)
                {
                    searchView.BeginInvoke(new UpdateDataGridViewCallback(
                        () => this.DisplayFlightsByDateDestination(flight.Destination, flight.Date.ToString("dd/MM/yyyy"))
                    ));
                }
            }
        }
        
        //define a delegate to be called back by the GUI Thread
        public delegate void UpdateDataGridViewCallback();
        
    }
}