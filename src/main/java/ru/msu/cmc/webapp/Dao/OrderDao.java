package ru.msu.cmc.webapp.Dao;

import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;

import java.util.List;

public interface OrderDao {
    void createOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(Order order);
    Order getOrderByID(int id);
    List<Order> getOrdersByClientId(Client client);
    List<Order> getAllOrders();
//    List<Order> getOpenOrders();
}
