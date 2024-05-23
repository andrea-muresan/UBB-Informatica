package app.repository.DBRepositories;

import app.domain.Client;
import app.repository.IClientRepository;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class ClientDBRepository implements IClientRepository {

    public ClientDBRepository() {
    }

    @Override
    public Client findByCredentials(String username, String password) {
        Client user = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            try (PreparedStatement preStmt = con.prepareStatement("select * from client where username=? and password=?")) {
                preStmt.setString(1, username);
                preStmt.setString(2, password);
                try (ResultSet result = preStmt.executeQuery()) {
                    while (result.next()) {
                        Integer id = result.getInt("id");
                        String firstName = result.getString("first_name");
                        String lastName = result.getString("last_name");
                        String CNP = result.getString("CNP");
                        String address = result.getString("address");

                        user = new Client(username, password, firstName, lastName, CNP, address);
                        user.setId(id);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Client> getAll() {
        return null;
    }
}
