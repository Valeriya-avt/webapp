package ru.msu.cmc.webapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.Dao.AdminDao;
import ru.msu.cmc.webapp.Dao.BookDao;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Dao.Impl.AdminDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Models.Admin;
import ru.msu.cmc.webapp.Models.Book;
import ru.msu.cmc.webapp.Models.Client;

import java.util.List;

@Controller
public class BookController {
    BookDao bookDao = new BookDaoImpl();
    AdminDao adminDao = new AdminDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();

    @GetMapping("/books")
    public String booksPage(@CookieValue(value = "client_login", defaultValue = "client_login") String client_login_cookie,
                            @CookieValue(value = "client_password", defaultValue = "client_password") String client_password_cookie,
                            Model model) {
        List<Book> books = bookDao.getAllBooks();
        Client client = clientDao.getClientByLogin(client_login_cookie);
        Admin admin = adminDao.getAdminByLogin(client_login_cookie);
        model.addAttribute("books", books);
        if (books.size() == 0) {
            return "errorPage";
        }
        return "books";
    }

    @GetMapping({"/book", "/bookForAdmin", "/bookForClient"})
    public String bookPage(@RequestParam(name = "book_id") int book_id,
                           @CookieValue(value = "client_login", defaultValue = "client_login") String client_login_cookie,
                           @CookieValue(value = "client_password", defaultValue = "client_password") String client_password_cookie,
                           Model model) {
        Book book = bookDao.getBookByID(book_id);
        Client client = clientDao.getClientByLogin(client_login_cookie);
        Admin admin = adminDao.getAdminByLogin(client_login_cookie);
        model.addAttribute("book", book);
        if (book == null) {
            return "errorPage";
        }
        if (admin != null) {
            if (admin.getAdmin_login().equals(client_login_cookie) &&
            admin.getAdmin_password().equals(client_password_cookie)) {
                return "bookForAdmin";
            }
        }
        if (client != null) {
            if (client.getClient_login().equals(client_login_cookie) && client.getClient_password().equals(client_password_cookie)) {
                return "bookForClient";
            }
        }
        return "bookInfo";
    }

    @GetMapping("/editBook")
    public String editBookPage(@RequestParam(name = "book_id") int book_id,
                               @CookieValue(value = "client_login", defaultValue = "client_login") String client_login_cookie,
                               @CookieValue(value = "client_password", defaultValue = "client_password") String client_password_cookie,
                               Model model) {
        Book book = bookDao.getBookByID(book_id);
        Admin admin = adminDao.getAdminByLogin(client_login_cookie);
        if (admin != null) {
            if (admin.getAdmin_login().equals(client_login_cookie) &&
                    admin.getAdmin_password().equals(client_password_cookie)) {
                model.addAttribute("book", book);
                return "editBook";
            }
        }
        return "errorPage";
    }

    @PostMapping("/updateBook")
    public String updateBookPage(@RequestParam(name = "book_id") int book_id,
                                 @RequestParam(name = "title") String title,
                                 @RequestParam(name = "authors") String authors,
                                 @RequestParam(name = "genre") String genre,
                                 @RequestParam(name = "publishing_house") String publishing_house,
                                 @RequestParam(name = "publishing_year") int publishing_year,
                                 @RequestParam(name = "num_of_pages") int num_of_pages,
                                 @RequestParam(name = "cover_type") String cover_type,
                                 @RequestParam(name = "price") Double price,
                                 @RequestParam(name = "amount") int amount,
                                 @CookieValue(value = "client_login", defaultValue = "client_login") String client_login_cookie,
                                 @CookieValue(value = "client_password", defaultValue = "client_password") String client_password_cookie,
                                 Model model) {
        Admin admin = adminDao.getAdminByLogin(client_login_cookie);
        if (admin != null) {
            if (admin.getAdmin_login().equals(client_login_cookie) &&
                    admin.getAdmin_password().equals(client_password_cookie)) {
                Book book = bookDao.getBookByID(book_id);
                book.setTitle(title);
                book.setAuthors(authors);
                book.setGenre(genre);
                book.setPublishing_house(publishing_house);
                book.setPublishing_year(publishing_year);
                book.setNum_of_pages(num_of_pages);
                book.setCover_type(cover_type);
                book.setPrice(price);
                book.setAmount(amount);
                bookDao.updateBook(book);
                if (bookDao.getBookByID(book.getBook_id()) == null) {
                    return "errorPage";
                }
                model.addAttribute("book", book);
                return "editBook";
            }
        }
        return "errorPage";
    }
}
