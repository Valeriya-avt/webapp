package ru.msu.cmc.webapp.Dao;

import ru.msu.cmc.webapp.Models.Book;

import java.util.List;

public interface BookDao {
    void createBook(Book book);
    void updateBook(Book book);
    void deleteBook(Book book);
    Book getBookByID(int id);
    List<Book> getBooksByTitle(String title);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByGenre(String genre);
    List<Book> getBooksByPublishingHouse(String publishing_house);
    List<Book> getBooksByPublishingYear(int publishing_year);
    double getBookPrice(Book book);
    int getBookAmount(Book book);
    List<Book> getAllBooks();
}
