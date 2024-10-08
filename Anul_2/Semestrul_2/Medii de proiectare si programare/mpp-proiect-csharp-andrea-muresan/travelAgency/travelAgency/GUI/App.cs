using System;
using System.Collections.Generic;
using System.Data;
using System.Windows.Forms;
using travelAgency.domain;
using travelAgency.service;

namespace travelAgency.GUI;

public partial class App : Form
{
    private Service service;

    private DataTable flightsViewTable = new DataTable();
    
    public App(Service service)
    {
        this.service = service;
        
        InitializeComponent();
        
        // Configure DataGridViews
        // Controls.Add(flightsView);
        DisplayFlights(); 
       
    }
    
    private void DisplayFlights()
    {
        // Call the function to get a list of flights
        IEnumerable<Flight> flights = service.GetAllFlights();

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
           if (flight.NoSeats != 0) {
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
        IEnumerable<Flight> flights = service.GetFlightsByDateDestination(destination, date);

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
            Flight flight = service.FindFlight(flightId); 
            string endString = service.buyTicket(flightId, clientName, clientAddress, touristsNames, noSeats);
            DisplayFlights();
            if (flight != null) DisplayFlightsByDateDestination(flight.Destination, flight.Date.ToString("dd/MM/yyyy"));
            MessageBox.Show(endString);
        }
    }

    private void logOutBtn_Click(object sender, EventArgs e)
    {
        this.Close();
    }
    
}