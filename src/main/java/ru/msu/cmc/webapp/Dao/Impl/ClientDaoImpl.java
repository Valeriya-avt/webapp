package ru.msu.cmc.webapp.Dao.Impl;

import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Utils.HibernateUtil;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ClientDaoImpl implements ClientDao {
    @Override
    public void createClient(Client client) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("createClient Exception: " + e.getMessage());
        }
    }

    @Override
    public void updateClient(Client client) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(client);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("updateClient Exception: " + e.getMessage());
        }
    }

    @Override
    public void deleteClient(Client client) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(client);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            System.out.println("deleteClient Exception: " + e.getMessage());
        }
    }

    @Override
    public Client getClientByID(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Client> query = session.createQuery("FROM Client WHERE client_id =: id", Client.class).setParameter("id", id);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }


    @Override
    public Client getClientByLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Client> query = session.createQuery("FROM Client WHERE client_login =: login", Client.class).setParameter("login", login);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }

    @Override
    public List<Client> getClientsByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Client> query = session.createQuery("FROM Client WHERE name =: name", Client.class).setParameter("name", name);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }

    @Override
    public List<Client> getClientsBySurname(String surname) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Client> query = session.createQuery("FROM Client WHERE surname =: surname", Client.class).setParameter("surname", surname);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }

    @Override
    public List<Client> getAllClient() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Client> query = session.createQuery("FROM Client", Client.class);
        return query.getResultList();
    }
}
