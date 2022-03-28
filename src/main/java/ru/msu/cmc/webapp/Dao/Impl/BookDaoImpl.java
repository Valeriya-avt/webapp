package ru.msu.cmc.webapp.Dao.Impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.msu.cmc.webapp.Dao.BookDao;
import ru.msu.cmc.webapp.Models.Book;
import ru.msu.cmc.webapp.Utils.HibernateUtil;

import java.util.List;

public class BookDaoImpl implements BookDao {
    @Override
    public void createBook(Book book) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(book);
            transaction.commit();
            session.close();
        } catch (Exception e) {
//            System.out.println("createBook Exception: " + e.getMessage());
        }
    }

    @Override
    public void updateBook(Book book) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(book);
            transaction.commit();
            session.close();
        } catch (Exception e) {
//            System.out.println("updateBook Exception: " + e.getMessage());
        }

    }

    @Override
    public void deleteBook(Book book) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(book);
            transaction.commit();
            session.close();
        } catch (Exception e) {
//            System.out.println("deleteBook Exception: " + e.getMessage());
        }
    }

    @Override
    public Book getBookByID(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Book> query = session.createQuery("FROM Book WHERE book_id =: id", Book.class).setParameter("id", id);
        if (query.getResultList().size() == 0) {
            return null;
        }
        return query.getResultList().get(0);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Book> query = session.createQuery("FROM Book WHERE title =: title", Book.class).setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Book> query = session.createQuery("FROM Book WHERE authors =: author", Book.class).setParameter("author", author);
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Book> query = session.createQuery("FROM Book WHERE genre =: genre", Book.class).setParameter("genre", genre);
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByPublishingHouse(String publishing_house) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Book> query = session.createQuery("FROM Book WHERE publishing_house =: publishing_house", Book.class).setParameter("publishing_house", publishing_house);
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByPublishingYear(int publishing_year) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Book> query = session.createQuery("FROM Book WHERE publishing_year =: publishing_year", Book.class).setParameter("publishing_year", publishing_year);
        return query.getResultList();
    }

    @Override
    public double getBookPrice(Book book) {
        return book.getPrice();
    }

    @Override
    public int getBookAmount(Book book) {
        return book.getAmount();
    }

    @Override
    public List<Book> getAllBooks() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Book> query = session.createQuery("FROM Book", Book.class);
        return query.getResultList();
    }
}
