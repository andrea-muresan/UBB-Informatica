using System;
using System.Collections.Generic;
using travelAgency.domain;

namespace travelAgency.repository
{

    public interface IFlightRepository : ICrudRepository<int, Flight>
    {
        List<Flight> FindByDestinationDate(String destination, String date);
    }
}