package app.repository.hibernate;

import app.domain.Book;
import app.domain.Client;
import app.repository.IClientRepository;
import org.hibernate.Session;

import java.util.List;

public class ClientHibernateRepository implements IClientRepository {
    @Override
    public Client findByCredentials(String username, String password) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Client where username=:username and password=:password", Client.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public Client findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Client where id=:id ", Client.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public Client save(Client entity) {
        return null;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public Client delete(Integer id) {
        return null;
    }

    @Override
    public List<Client> getAll() {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Client ", Client.class).getResultList();
        }
    }
}