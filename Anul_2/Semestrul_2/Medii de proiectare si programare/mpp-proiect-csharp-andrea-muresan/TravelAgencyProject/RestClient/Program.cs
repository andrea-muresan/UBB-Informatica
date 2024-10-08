using System;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading;
using System.Threading.Tasks;
using Model;
using Newtonsoft.Json;


namespace RestClient
{
	class Program
	{
        static HttpClient Client = new HttpClient();
        static string Url = "http://localhost:8080/app/flights";

        public static void Main(string[] args)
        {
            RunAsync().Wait();
        }

        static async Task RunAsync()
        {
            Client.DefaultRequestHeaders.Accept.Clear();
            Client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

            // get by id
            Console.WriteLine("Get by id...");
            FlightRest aux = await GetByIdAsync(Url + "/2");
            Console.WriteLine(aux);

            // create
            Console.WriteLine("Create...");
            FlightRest newFlight = new FlightRest("test", "2024-07-09", "13:00:00", "test", 6);
            
            aux = await AddFlightAsync(Url, newFlight);
            Console.WriteLine(aux);
            //
            // // find all
            // Console.WriteLine("Find all...");
            // FlightRest[] flights = await GetAllAsync(Url);
            // PrintAsTable(flights);
            
            // // update
            // Console.WriteLine("Update...");
            // FlightRest updateFlight = flights[flights.Length - 1];
            // updateFlight.Destination = "testRestC#";
            // string response = await UpdateFlightAsync(Url, updateFlight);
            // Console.WriteLine("Update response: " + response);
            
            
            // //find all
            // Console.WriteLine("Find all...");
            // flights = await GetAllAsync(Url);
            // PrintAsTable(flights);
            
            // // delete
            // Console.WriteLine("Delete...");
            // response = await DeleteGameAsync(Url + "/" + updateGame.id.ToString());
            // Console.WriteLine("Delete response: " + response);
            //
            // // find all
            // Console.WriteLine("Find all...");
            // games = await GetAllAsync(Url);
            // PrintAsTable(games);

        }

        static async Task<FlightRest> GetByIdAsync(string path)
        {
            FlightRest flight = null;
            HttpResponseMessage response = await Client.GetAsync(path);
            if(response.IsSuccessStatusCode)
                flight = await response.Content.ReadAsAsync<FlightRest>();
            return flight;
        }

    static async Task<FlightRest> AddFlightAsync(string path, FlightRest flight)
    {
        FlightRest fl = null;
        HttpResponseMessage response = await Client.PostAsJsonAsync(path, flight);
        if (response.IsSuccessStatusCode)
        {
            fl = await response.Content.ReadAsAsync<FlightRest>();
        }
        return fl;
    }
    
    static async Task<FlightRest[]> GetAllAsync(string path)
    {
        FlightRest[] flights = null;
        HttpResponseMessage response = await Client.GetAsync(path);
        if (response.IsSuccessStatusCode)
        {
            flights = await response.Content.ReadAsAsync<FlightRest[]>();
        }
        return flights;
    }
    
    static async Task<string> UpdateFlightAsync(string path, FlightRest flight)
    {
        HttpResponseMessage response = await Client.PutAsJsonAsync(path, flight);
        if (!response.IsSuccessStatusCode)
        {
            return "can not update!";
        }
        return "ok";
    }
    //
    //     static async Task<string> DeleteflightAsync(string path)
    //     {
    //         HttpResponseMessage response = await Client.DeleteAsync(path);
    //         if (!response.IsSuccessStatusCode)
    //         {
    //             return "can not delete!";
    //         }
    //         return "ok";
    //     }
    //
        private static void PrintAsTable(FlightRest[] flights)
        {
            foreach (FlightRest fl in flights)
            {
                Console.WriteLine(fl);
            }
        }
 

    public class FlightRest 
    {
        public int Id { get; set; }
        public string Destination { get; set; }
        public string Date { get; set; }
        public string Time { get; set; }
        public string Airport { get; set; }
        public int NoSeats { get; set; }

        public FlightRest()
        {
            
        }

        public FlightRest(string destination, string date, string time, string airport, int noSeats)
        {
            Destination = destination;
            Date = date;
            Time = time;
            Airport = airport;
            NoSeats = noSeats;
        }

        public FlightRest(Flight fl)
        {
            if (fl != null)
            {
                this.Id = fl.Id;
                this.Destination = fl.Destination;
                string date = fl.Date.ToString("yyyy-MM-dd");
                string time = fl.Date.ToString("HH:mm:ss");
                this.Airport = fl.Airport;
                this.NoSeats = fl.NoSeats;
            }
            
        }
        
        public override string ToString()
        {
            return "Flight: " + Id + " " + Destination + " " + Date + " " +
                   Time + " " + Airport + " " + NoSeats;
        }
    }
	}
}