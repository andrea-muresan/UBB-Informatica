package app.repository.DBRepositories;

import app.domain.Client;
import app.domain.Librarian;
import app.repository.ILibrarianRepository;

import java.sql.*;
import java.util.List;

public class LibrarianDBRepository implements ILibrarianRepository {

    public LibrarianDBRepository() {
    }

    @Override
    public Librarian findByCredentials(String username, String password){
        Librarian user = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:library.db");
            try (PreparedStatement preStmt = con.prepareStatement("select * from librarian where username=? and password=?")) {
                preStmt.setString(1, username);
                preStmt.setString(2, password);
                try (ResultSet result = preStmt.executeQuery()) {
                    while (result.next()) {
                        Integer id = result.getInt("id");
                        user = new Librarian(username, password);
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
    public Librarian findOne(Integer id) {
        return null;
    }

    @Override
    public Librarian save(Librarian entity) {
        return null;
    }

    @Override
    public Librarian update(Librarian entity) {
        return null;
    }

    @Override
    public Librarian delete(Integer id) {
        return null;
    }

    @Override
    public List<Librarian> getAll() {
        return null;
    }
}
