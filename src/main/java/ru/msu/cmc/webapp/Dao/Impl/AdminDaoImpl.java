package ru.msu.cmc.webapp.Dao.Impl;

import ru.msu.cmc.webapp.Models.Admin;
import ru.msu.cmc.webapp.Dao.AdminDao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.msu.cmc.webapp.Utils.HibernateUtil;

public class AdminDaoImpl implements AdminDao {
    @Override
    public void createAdmin(Admin admin) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("createAdmin Exception: " + e.getMessage());
        }

    }

    @Override
    public void updateAdmin(Admin admin) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(admin);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("updateAdmin Exception: " + e.getMessage());
        }
    }

    @Override
    public void deleteAdmin(Admin admin) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(admin);
            transaction.commit();
            session.close();
        } finally {
//            System.out.println("deleteAdmin Exception: " + e.getMessage());
        }
    }

    @Override
    public Admin getAdminByID(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Admin admin = session.get(Admin.class, id);
        session.close();
        return admin;
    }

    @Override
    public Admin getAdminByLogin(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Admin> query = session.createQuery("FROM Admin WHERE admin_login =: param", Admin.class).setParameter("param", login);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0); //
    }
}
