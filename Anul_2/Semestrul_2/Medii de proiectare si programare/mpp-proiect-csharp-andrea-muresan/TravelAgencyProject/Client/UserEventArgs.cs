using System;

namespace Client
{
    public enum FlightEvent
    {
        Purchase
    } ;
    public class FlightEventArgs : EventArgs
    {
        private readonly FlightEvent flightEvent;
        private readonly Object data;

        public FlightEventArgs(FlightEvent flightEvent, object data)
        {
            this.flightEvent = flightEvent;
            this.data = data;
        }

        public FlightEvent FlightEventType
        {
            get { return flightEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}