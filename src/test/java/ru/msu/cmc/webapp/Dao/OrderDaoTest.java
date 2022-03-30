package ru.msu.cmc.webapp.Dao;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.OrderDaoImpl;
import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;

import java.util.List;

public class OrderDaoTest {
    private final OrderDao orderDao = new OrderDaoImpl();
    private final ClientDao clientDao = new ClientDaoImpl();

    @Test
    public void createOrder() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Assert.assertEquals(orderDao.getOrderByID(order.getOrder_id()), order);
        orderDao.deleteOrder(order);
        clientDao.deleteClient(client);
    }

    @Test
    public void updateOrder() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Assert.assertEquals(orderDao.getOrderByID(order.getOrder_id()), order);
        order.setStatus("delivered");
        orderDao.updateOrder(order);
        Assert.assertEquals(orderDao.getOrderByID(order.getOrder_id()), order);
        orderDao.deleteOrder(order);
        clientDao.deleteClient(client);
    }

    @Test
    public void deleteOrder() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Assert.assertEquals(orderDao.getOrderByID(order.getOrder_id()), order);
        orderDao.deleteOrder(order);
        clientDao.deleteClient(client);
        Assert.assertNull(orderDao.getOrderByID(order.getOrder_id()));
    }

    @Test
    public void getOrdersByClientId() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        List<Order> expectedOrders = List.of(
                new Order(client, java.sql.Date.valueOf("2022-02-01"), java.sql.Date.valueOf("2022-02-24"), "processed"),
                new Order(client, java.sql.Date.valueOf("2022-02-23"), java.sql.Date.valueOf("2022-02-25"), "processed"),
                new Order(client, java.sql.Date.valueOf("2022-02-25"), java.sql.Date.valueOf("2022-02-26"), "processed")
        );
        orderDao.createOrder(expectedOrders.get(0));
        orderDao.createOrder(expectedOrders.get(1));
        orderDao.createOrder(expectedOrders.get(2));
        List<Order> actualOrders = orderDao.getOrdersByClientId(client);

        Assert.assertEquals(actualOrders.get(0), expectedOrders.get(0));
        Assert.assertEquals(actualOrders.get(1), expectedOrders.get(1));
        Assert.assertEquals(actualOrders.get(2), expectedOrders.get(2));

        orderDao.deleteOrder(expectedOrders.get(0));
        orderDao.deleteOrder(expectedOrders.get(1));
        orderDao.deleteOrder(expectedOrders.get(2));
        clientDao.deleteClient(client);
    }

    @Test
    public void testGetAllOrders() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        List<Order> expectedOrders = List.of(
                new Order(client, java.sql.Date.valueOf("2022-02-01"), java.sql.Date.valueOf("2022-02-24"), "processed"),
                new Order(client, java.sql.Date.valueOf("2022-02-23"), java.sql.Date.valueOf("2022-02-25"), "processed"),
                new Order(client, java.sql.Date.valueOf("2022-02-25"), java.sql.Date.valueOf("2022-02-26"), "processed")
        );
        orderDao.createOrder(expectedOrders.get(0));
        orderDao.createOrder(expectedOrders.get(1));
        orderDao.createOrder(expectedOrders.get(2));
        List<Order> actualOrders = orderDao.getAllOrders();

        Assert.assertEquals(actualOrders.get(0), expectedOrders.get(0));
        Assert.assertEquals(actualOrders.get(1), expectedOrders.get(1));
        Assert.assertEquals(actualOrders.get(2), expectedOrders.get(2));

        orderDao.deleteOrder(expectedOrders.get(0));
        orderDao.deleteOrder(expectedOrders.get(1));
        orderDao.deleteOrder(expectedOrders.get(2));
        clientDao.deleteClient(client);
    }
}
