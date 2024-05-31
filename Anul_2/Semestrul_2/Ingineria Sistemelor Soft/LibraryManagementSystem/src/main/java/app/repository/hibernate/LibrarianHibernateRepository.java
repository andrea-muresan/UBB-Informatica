package app.repository.hibernate;

import app.domain.Librarian;
import app.repository.ILibrarianRepository;
import org.hibernate.Session;

import java.util.List;

public class LibrarianHibernateRepository implements ILibrarianRepository {
    @Override
    public Librarian findByCredentials(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Librarian where username=:username and password=:password", Librarian.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResultOrNull();
        }
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
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Librarian ", Librarian.class).getResultList();
        }
    }
}
