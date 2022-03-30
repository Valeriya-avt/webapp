package ru.msu.cmc.webapp.Dao.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.msu.cmc.webapp.Dao.OrderDao;
import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;
import ru.msu.cmc.webapp.Utils.HibernateUtil;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    public void createOrder(Order order) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("createOrder Exception: " + e.getMessage());
        }
    }

    public void updateOrder(Order order) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("updateOrder Exception: " + e.getMessage());
        }
    }

    public void deleteOrder(Order order) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(order);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("deleteOrder Exception: " + e.getMessage());
        }

    }

    @Override
    public Order getOrderByID(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Order> query = session.createQuery("FROM Order WHERE order_id =: id", Order.class).setParameter("id", id);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }

    @Override
    public List<Order> getOrdersByClientId(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Order> query = session.createQuery("FROM Order WHERE client_id =: param", Order.class).setParameter("param", client);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }

    @Override
    public List<Order> getAllOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Order> criteriaQuery = session.getCriteriaBuilder().createQuery(Order.class);
        criteriaQuery.from(Order.class);
        List<Order> allOrders = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return allOrders;
    }
}
