using System;
using Model.dto;

namespace Networking
{
    
    
    public interface Request 
    {
    }


    [Serializable]
    public class LoginRequest : Request
    {
        private UserDto user;

        public LoginRequest(UserDto user)
        {
            this.user = user;
        }

        public virtual UserDto User
        {
            get
            {
                return user;
            }
        }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        private UserDto user;

        public LogoutRequest(UserDto user)
        {
            this.user = user;
        }

        public virtual UserDto User
        {
            get
            {
                return user;
            }
        }
    }

    // [Serializable]
    // public class SendMessageRequest : Request
    // {
    //     private MessageDTO message;
    //
    //     public SendMessageRequest(MessageDTO message)
    //     {
    //         this.message = message;
    //     }
    //
    //     public virtual MessageDTO Message
    //     {
    //         get
    //         {
    //             return message;
    //         }
    //     }
    // }
    //
    [Serializable]
    public class GetFlightsRequest : Request
    { 
        public GetFlightsRequest() { }
    }
    
    [Serializable]
    public class GetFlightsDateDestRequest : Request
    { 
        public SearchFlightsDto searchFlightDetails { get; }

        public GetFlightsDateDestRequest(SearchFlightsDto sFlDto)
        {
            this.searchFlightDetails = sFlDto;
        }
    }
    
    [Serializable]
    public class PurchaseRequest : Request 
    { 
        public TicketPurchaseDto ticketPurchase { get; }
        public PurchaseRequest(TicketPurchaseDto ticketPurchase)
        {
            this.ticketPurchase = ticketPurchase;
        }
    }
}