package ru.msu.cmc.webapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.Dao.AdminDao;
import ru.msu.cmc.webapp.Dao.BookDao;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Dao.Impl.AdminDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.OrderDaoImpl;
import ru.msu.cmc.webapp.Dao.OrderDao;
import ru.msu.cmc.webapp.Models.Admin;
import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;

import java.util.List;

@Controller
public class OrderController {
    AdminDao adminDao = new AdminDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();
    OrderDao orderDao = new OrderDaoImpl();

    @GetMapping("/order")
    public String bookPage(@RequestParam(name = "order_id") int order_id,
                           @CookieValue(value = "login", defaultValue = "defaultLogin") String client_login_cookie,
                           @CookieValue(value = "password", defaultValue = "defaultPassword") String client_password_cookie,
                           Model model) {
        Order order = orderDao.getOrderByID(order_id);
        Client client = clientDao.getClientByLogin(client_login_cookie);
        model.addAttribute("order", order);
        if (client != null && order != null) {
            if (client.getClient_password().equals(client_password_cookie)) {
                if (order.getStatus().equals("Открыт")) {
                    return "openOrder";
                }
                return "closeOrder";
            }
        }
        return "errorPage";
    }

    @GetMapping("/orders")
    public String ordersPage(@CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                             @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                             Model model) {
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                List<Order> orders = orderDao.getAllOrders();
                model.addAttribute("orders", orders);
                return "orders";
            }
        }
        return "errorPage";
    }

    @GetMapping("/clientOrders")
    public String clientOrdersPage(@RequestParam(name = "client_id") int client_id,
                                   @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                                   @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                                   Model model) {
        Client orders_owner = clientDao.getClientByID(client_id);
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                List<Order> orders = orderDao.getOrdersByClientId(orders_owner);
                model.addAttribute("orders", orders);
                return "clientOrders";
            }
        }
        return "errorPage";
    }

    @GetMapping("/checkout")
    public String checkoutPage(@RequestParam(name = "order_id") int order_id,
                               @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                               @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                               Model model) {
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                Order order = orderDao.getOrderByID(order_id);
                if (order.getOrder_price() == 0) {
                    model.addAttribute("error_msg", "Выберите товар для заказа");
                    return "errorPage";
                }
                order.setClient_id(client);
                order.setStatus("Закрыт");
                orderDao.updateOrder(order);
                if (orderDao.getOrderByID(order.getOrder_id()) == null) {
                    return "errorPage";
                }
                model.addAttribute("order", order);
                return "congratulationsOnCheckout";
            }
        }
        return "errorPage";
    }
}
