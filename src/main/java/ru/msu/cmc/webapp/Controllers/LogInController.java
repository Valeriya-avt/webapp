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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LogInController {
    AdminDao adminDao = new AdminDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();
    BookDao bookDao = new BookDaoImpl();

    @GetMapping("/logged")
    public String indexPage(@CookieValue(value = "login", defaultValue = "defaultLogin") String client_login,
                        @CookieValue(value = "password", defaultValue = "defaultPassword") String client_password,
                        Model model) {
        List<Book> books = bookDao.getAllBooks();
        model.addAttribute("books", books);
        Admin admin = adminDao.getAdminByLogin(client_login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(client_password)) {
                return "booksForAdmin";
            }
        }
        Client client = clientDao.getClientByLogin(client_login);
        if (client != null) {
            if (client_password.equals(client.getClient_password())) {
                model.addAttribute("client_id", client.getClient_id());
                return "booksForClient";
            }
        }
        return "errorPage";
    }

    @GetMapping("/logIn")
    public String logInPage(@CookieValue(value = "login", defaultValue = "defaultLogin") String login, Model model) {
//        Admin admin = adminDao.getAdminByLogin(login);
//        Client client = clientDao.getClientByLogin(login);
//        if (admin != null) {
//            return "errorPage";
//        }
//        if (client != null) {
//            return "errorPage";
//        }
        return "logIn";
    }

    @GetMapping("/logOut")
    public String logOutPage(HttpServletResponse response) {
        Cookie login = new Cookie("login", "login");
        response.addCookie(login);
        Cookie password = new Cookie("password", "password");
        response.addCookie(password);
        return "redirect:/";
    }

    @PostMapping("/identification")
    public String clientCheck(HttpServletResponse response,
                              @RequestParam(name = "login") String login,
                              @RequestParam(name = "password") String password,
                              Model model) {
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            System.out.println(admin.getAdmin_id());
            System.out.println(admin.getAdmin_login());
            if (!admin.getAdmin_password().equals(password)) {
                model.addAttribute("error_msg", "ERROR 1");
                return "errorPage";
            } else {
                List<Book> books = bookDao.getAllBooks();
                model.addAttribute("books", books);
                Cookie login_cookie = new Cookie("login", login);
                response.addCookie(login_cookie);
                Cookie password_cookie = new Cookie("password", password);
                response.addCookie(password_cookie);
                model.addAttribute("admin_id", admin.getAdmin_id());
                return "booksForAdmin";
            }
        }
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (!client.getClient_password().equals(password)) {
                return "errorPage";
            } else {
                List<Book> books = bookDao.getAllBooks();
                model.addAttribute("books", books);
                Cookie login_cookie = new Cookie("login", login);
                response.addCookie(login_cookie);
                Cookie password_cookie = new Cookie("password", password);
                response.addCookie(password_cookie);
                model.addAttribute("client_id", client.getClient_id());
                return "booksForClient";
            }
        }
        return "errorPage";
    }
}
