package app.domain;

import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table( name = "librarian" )
public class Librarian extends User{
    public Librarian(String username, String password) {
        super(username, password);
    }

    public Librarian() {

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
