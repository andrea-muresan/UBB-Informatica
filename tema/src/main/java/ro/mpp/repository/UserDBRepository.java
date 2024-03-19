package ro.mpp.repository;

import ro.mpp.domain.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class UserDBRepository implements UserRepository {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UserDBRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<User> findOne(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<User> users=new ArrayList<>();
        try (PreparedStatement preStmt=con.prepareStatement("select * from users")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(username, password);
                    user.setId(id);
                    users.add(user);
                }
            }
        } catch (SQLException e) {

            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(users);
        return users;
    }

    @Override
    public void save(User entity) {
        logger.traceEntry( "saving task {}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into users (username, password) values (?,?)")) {
            preStmt.setString(1,entity.getUsername());
            preStmt.setString(2,entity.getPassword());
            int result=preStmt.executeUpdate();
            logger.trace("Saved () instances", result);
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
            logger.traceExit();
        }
    }

    @Override
    public void delete(Integer integer) {
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public User findByUsername(String username) {
        logger.traceEntry();
        User user = null;
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preStmt=con.prepareStatement("select * from users where username=?")) {
            preStmt.setString(1, username);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String password = result.getString("password");
                    user =  new User(username, password);
                    user.setId(id);
                }
            }
        } catch (SQLException e) {

            logger.error(e);
            System.err.println("Error DB " + e);
        }
        logger.traceExit(user);
        return user;
    }
}
