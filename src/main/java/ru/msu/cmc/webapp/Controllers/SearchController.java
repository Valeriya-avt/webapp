package ru.msu.cmc.webapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
public class SearchController {
    AdminDao adminDao = new AdminDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();
    BookDao bookDao = new BookDaoImpl();

    @PostMapping("/search")
    public String bookPage(@RequestParam(name = "title") String title,
                           @CookieValue(name = "login") String login,
                           @CookieValue(name = "password") String password,
                           Model model) {
        List<Book> books = bookDao.getAllBooks();
        if (!title.equals("")) {
            books = bookDao.getBooksByTitle(title);
        }
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                model.addAttribute("books", books);
                return "booksForAdmin";
            }
        }
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                model.addAttribute("books", books);
                return "booksForClient";
            }
        }
        model.addAttribute("books", books);
        return "books";
    }
}
