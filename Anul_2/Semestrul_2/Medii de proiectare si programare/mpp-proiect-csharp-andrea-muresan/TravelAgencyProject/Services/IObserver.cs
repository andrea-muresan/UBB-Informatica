using Model;

namespace Services
{
    public interface IObserver
    {
        void UpdateFlight(Flight flight);
    }
}