package persistence.repository;

import app.model.Flight;
import app.model.Ticket;
import persistence.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class TicketDBRepository implements TicketRepository {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public TicketDBRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }
    @Override
    public Flight findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        return null;
    }

    @Override
    public void save(Ticket entity) throws SQLException {
        logger.traceEntry( "saving task {}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into tickets (flight_id, client_name, client_address, tourists_names, no_seats) values (?,?,?,?,?)")) {
            preStmt.setInt(1,entity.getFlight().getId());
            preStmt.setString(2,entity.getClientName());
            preStmt.setString(3,entity.getClientAddress());
            preStmt.setString(4,entity.getTouristsNames());
            preStmt.setInt(5,entity.getNoSeats());
            int result=preStmt.executeUpdate();
            logger.trace("Saved () instances", result);
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            logger.traceExit();
            throw ex;

        }
    }

    @Override
    public void delete(Integer integer) {
    }

    @Override
    public void update(Ticket entity) {

    }
}
