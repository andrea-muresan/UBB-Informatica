package ro.ubbcluj.cs.map.repository;

import ro.ubbcluj.cs.map.domain.Friendship;
import ro.ubbcluj.cs.map.domain.User;
import ro.ubbcluj.cs.map.domain.validators.Validator;

import java.sql.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendshipDBRepository implements Repository<Long, Friendship> {

    private final String url;
    private final String user;
    private final String password;

    private final Validator<Friendship> validator;


    public FriendshipDBRepository(String url, String user, String password, Validator<Friendship> validator) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public Optional<Friendship> findOne(Long longID) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships WHERE id=?")) {
            statement.setLong(1, longID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long user1_id = resultSet.getLong("user1_id");
                Long user2_id = resultSet.getLong("user2_id");
                LocalDateTime friends_from = resultSet.getTimestamp("friends_from").toLocalDateTime();
                Friendship f = new Friendship(user1_id, user2_id, friends_from);
                f.setId(longID);
                return Optional.of(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement("select * from friendships");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long user1_id = resultSet.getLong("user1_id");
                Long user2_id = resultSet.getLong("user2_id");
                LocalDateTime friends_from = resultSet.getTimestamp("friends_from").toLocalDateTime();

                Friendship f = new Friendship(user1_id, user2_id, friends_from);
                f.setId(id);
                friendships.add(f);
            }
            return friendships;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        validator.validate(entity);
        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("INSERT INTO friendships(user1_id,user2_id,friends_from) VALUES (?,?,?)"))
        {
            statement.setLong(1,entity.getUser1_id());
            statement.setLong(2,entity.getUser2_id());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getFriends_from()));
            int affectedRows = statement.executeUpdate();
            return affectedRows!=0? Optional.empty():Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> delete(Long longID) {
        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("DELETE FROM friendships WHERE ID = ?");)
        {
            Optional<Friendship> cv = findOne(longID);
            statement.setLong(1,longID);
            int affectedRows = statement.executeUpdate();
            return affectedRows==0? Optional.empty():cv;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        try(Connection connection = DriverManager.getConnection(url,user,password);
            PreparedStatement statement  = connection.prepareStatement("UPDATE friendships SET user1_id = ?, user2_id = ?, friends_from = ? WHERE id = ?"))
        {
            statement.setLong(1,entity.getUser1_id());
            statement.setLong(2,entity.getUser2_id());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getFriends_from()));
            statement.setLong(4,entity.getId());
            int affectedRows = statement.executeUpdate();
            return affectedRows!=0? Optional.empty():Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
