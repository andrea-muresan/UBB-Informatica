package app.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import app.model.Ticket;

import java.util.Properties;

public interface TicketRepository extends Repository<Integer, Ticket>{

}
