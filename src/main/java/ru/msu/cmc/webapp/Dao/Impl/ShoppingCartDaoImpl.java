package ru.msu.cmc.webapp.Dao.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.msu.cmc.webapp.Dao.ShoppingCartDao;
import ru.msu.cmc.webapp.Models.Order;
import ru.msu.cmc.webapp.Models.ShoppingCart;
import ru.msu.cmc.webapp.Utils.HibernateUtil;

import java.util.List;

public class ShoppingCartDaoImpl implements ShoppingCartDao {
    public void createShoppingCart(ShoppingCart shoppingCart) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("createShoppingCart Exception: " + e.getMessage());
        }
    }
    public void updateShoppingCart(ShoppingCart shoppingCart) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("updateShoppingCart Exception: " + e.getMessage());
        }
    }
    public void deleteShoppingCart(ShoppingCart shoppingCart) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(shoppingCart);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("deleteShoppingCart Exception: " + e.getMessage());
        }
    }
    @Override
    public ShoppingCart getShoppingCartById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<ShoppingCart> query = session.createQuery("FROM ShoppingCart WHERE shopping_cart_id =: id", ShoppingCart.class).setParameter("id", id);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }
    public List<ShoppingCart> getShoppingCartsByOrderId(Order order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<ShoppingCart> query = session.createQuery("FROM ShoppingCart WHERE order_id =: order_id", ShoppingCart.class).setParameter("order_id", order);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList();
    }
    public double getShoppingCartPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getPrice();
    }
}
