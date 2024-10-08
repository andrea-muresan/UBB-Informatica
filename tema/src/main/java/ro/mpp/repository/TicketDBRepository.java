package ro.mpp.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp.domain.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class TicketDBRepository implements TicketRepository{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public TicketDBRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }
    @Override
    public Optional<Ticket> findOne(Integer integer) {
        return Optional.empty();
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
