package ru.msu.cmc.webapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.Dao.BookDao;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.OrderDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ShoppingCartDaoImpl;
import ru.msu.cmc.webapp.Dao.OrderDao;
import ru.msu.cmc.webapp.Dao.ShoppingCartDao;
import ru.msu.cmc.webapp.Models.Book;
import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;
import ru.msu.cmc.webapp.Models.ShoppingCart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ShoppingCartController {
    ClientDao clientDao = new ClientDaoImpl();
    BookDao bookDao = new BookDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();
    ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();

    @GetMapping("/shoppingCart")
    public String shoppingCartPage(@CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                                   @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                                   Model model) {
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                Order openOrder = null;
                List<Order> clientOrders = orderDao.getOrdersByClientId(client);
                for (Order obj:clientOrders) {
                    if (obj.getStatus().equals("Открыт")) {
                        openOrder = obj;
                    }
                }
                List<ShoppingCart> shoppingCarts = shoppingCartDao.getShoppingCartsByOrderId(openOrder);
                if (shoppingCarts == null) {
                    List<ShoppingCart> emptyShoppingCart = new ArrayList<>();
                    model.addAttribute("shoppingCarts", emptyShoppingCart);
                    return "shoppingCart";
                }
                model.addAttribute("shoppingCarts", shoppingCarts);
                return "shoppingCart";
            }
        }
        return "errorPage";
    }

    @GetMapping("/createShoppingCart")
    public String createShoppingCartPage(@RequestParam(name = "book_id") int book_id,
                                         @CookieValue(value = "login", defaultValue = "defaultLogin")String login,
                                         @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                                         Model model) {
        Client client = clientDao.getClientByLogin(login);
        Book book = bookDao.getBookByID(book_id);
        Order openOrder = null;
        List<Order> clientOrders = orderDao.getOrdersByClientId(client);
        if (clientOrders != null) {
            for (Order obj : clientOrders) {
                if (obj.getStatus().equals("Открыт")) {
                    openOrder = obj;
                }
            }
        }
        if (openOrder == null) {
            java.util.Date util_date = new java.util.Date();
            java.sql.Date sql_order_time = new java.sql.Date(util_date.getTime());

            Calendar instance = Calendar.getInstance();
            instance.setTime(util_date);
            instance.add(Calendar.DAY_OF_MONTH, 3);
            java.sql.Date dev_time = new java.sql.Date(instance.getTime().getTime());

            if (client != null) {
                if (client.getClient_password().equals(password)) {
                    Order order = new Order(client, sql_order_time, dev_time, "Открыт");
                    orderDao.createOrder(order);
                }
            }
        }
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                model.addAttribute("book", book);
                return "createShoppingCart";
            }
        }
        return "errorPage";
    }

    @PostMapping("/addShoppingCart")
    public String addBookPage(@RequestParam(name = "book_id") int book_id,
                              @RequestParam(name = "amount") int amount,
                              @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                              @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                              Model model) {
        Client client = clientDao.getClientByLogin(login);
        Book book = bookDao.getBookByID(book_id);
        Order openOrder = null;
        List<Order> clientOrders = orderDao.getOrdersByClientId(client);
        for (Order obj:clientOrders) {
            if (obj.getStatus().equals("Открыт")) {
                openOrder = obj;
            }
        }

        if (!openOrder.getStatus().equals("Открыт")) {
            model.addAttribute("error_msg", "Заказ уже оформлен. Его нельзя изменить");
            return "errorPage";
        }
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                ShoppingCart shoppingCart = new ShoppingCart(openOrder, book, amount);
                shoppingCartDao.createShoppingCart(shoppingCart);
                return "configurationsOnCreateOrder";
            }
        }
        return "errorPage";
    }
}
