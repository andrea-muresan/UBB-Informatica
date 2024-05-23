package app.repository.hibernate;

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
    public List<Client> getAll() {
        try( Session session= HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Client ", Client.class).getResultList();
        }
    }
}
