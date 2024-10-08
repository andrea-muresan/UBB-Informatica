using System;
using System.Collections.Generic;
using Model;
using Persistence;


namespace Persistence
{

    public interface IFlightRepository : ICrudRepository<int, Flight>
    {
        List<Flight> FindByDestinationDate(String destination, String date);
    }
}