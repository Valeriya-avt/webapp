package ru.msu.cmc.webapp.Dao;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.OrderDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ShoppingCartDaoImpl;
import ru.msu.cmc.webapp.Models.Book;
import ru.msu.cmc.webapp.Models.Client;
import ru.msu.cmc.webapp.Models.Order;
import ru.msu.cmc.webapp.Models.ShoppingCart;

import java.util.List;

public class ShoppingCartDaoTest {
    private final OrderDao orderDao = new OrderDaoImpl();
    private final ClientDao clientDao = new ClientDaoImpl();
    private final BookDao bookDao = new BookDaoImpl();
    private final ShoppingCartDao shoppingCartDao = new ShoppingCartDaoImpl();

    @Test
    public void createShoppingCart() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        ShoppingCart shoppingCart = new ShoppingCart(100, order, book, 5400, 2);
        shoppingCartDao.createShoppingCart(shoppingCart);
        Assert.assertEquals(shoppingCartDao.getShoppingCartById(shoppingCart.getShopping_cart_id()), shoppingCart);
        clientDao.deleteClient(client);
        orderDao.deleteOrder(order);
        bookDao.deleteBook(book);
        shoppingCartDao.deleteShoppingCart(shoppingCart);
    }

    @Test
    public void updateShoppingCart() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        ShoppingCart shoppingCart = new ShoppingCart(100, order, book, 5400, 2);
        shoppingCartDao.createShoppingCart(shoppingCart);

        Assert.assertEquals(shoppingCartDao.getShoppingCartById(shoppingCart.getShopping_cart_id()), shoppingCart);
        shoppingCart.setAmount(3);
        shoppingCartDao.updateShoppingCart(shoppingCart);
        Assert.assertEquals(shoppingCartDao.getShoppingCartById(shoppingCart.getShopping_cart_id()).getAmount(), shoppingCart.getAmount());

        clientDao.deleteClient(client);
        orderDao.deleteOrder(order);
        bookDao.deleteBook(book);
        shoppingCartDao.deleteShoppingCart(shoppingCart);
    }

    @Test
    public void deleteShoppingCart() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        ShoppingCart shoppingCart = new ShoppingCart(100, order, book, 5400, 2);
        shoppingCartDao.createShoppingCart(shoppingCart);
        Assert.assertEquals(shoppingCartDao.getShoppingCartById(shoppingCart.getShopping_cart_id()), shoppingCart);
        clientDao.deleteClient(client);
        orderDao.deleteOrder(order);
        bookDao.deleteBook(book);
        shoppingCartDao.deleteShoppingCart(shoppingCart);
        Assert.assertNull(shoppingCartDao.getShoppingCartById(shoppingCart.getShopping_cart_id()));
    }

    @Test
    public void getShoppingCartsByOrderId() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);

        List<ShoppingCart> expectedShoppingCarts = List.of(
                new ShoppingCart(100, order, book, 5400, 2),
                new ShoppingCart(101, order, book, 5400, 2),
                new ShoppingCart(102, order, book, 5400, 2)
        );
        shoppingCartDao.createShoppingCart(expectedShoppingCarts.get(0));
        shoppingCartDao.createShoppingCart(expectedShoppingCarts.get(1));
        shoppingCartDao.createShoppingCart(expectedShoppingCarts.get(2));

        List<ShoppingCart> actualShoppingCarts = shoppingCartDao.getShoppingCartsByOrderId(order);

        Assert.assertEquals(actualShoppingCarts.get(0), expectedShoppingCarts.get(0));
        Assert.assertEquals(actualShoppingCarts.get(1), expectedShoppingCarts.get(1));
        Assert.assertEquals(actualShoppingCarts.get(2), expectedShoppingCarts.get(2));


        shoppingCartDao.deleteShoppingCart(expectedShoppingCarts.get(0));
        shoppingCartDao.deleteShoppingCart(expectedShoppingCarts.get(1));
        shoppingCartDao.deleteShoppingCart(expectedShoppingCarts.get(2));
        bookDao.deleteBook(book);
        orderDao.deleteOrder(order);
        clientDao.deleteClient(client);
    }

    @Test
    public void getShoppingCartPrice() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Order order = new Order(client, java.sql.Date.valueOf("2022-02-24"), java.sql.Date.valueOf("2022-02-26"), "processed");
        orderDao.createOrder(order);
        Book book = new Book(3004,"Дюна", "Фрэнк Герберт", "Фантастика", "АСТ", 2008, 736, "Твёрдый переплёт", 2700, 4);
        bookDao.createBook(book);
        ShoppingCart shoppingCart = new ShoppingCart(100, order, book, 5400, 2);
        shoppingCartDao.createShoppingCart(shoppingCart);
        Assert.assertEquals(shoppingCartDao.getShoppingCartById(shoppingCart.getShopping_cart_id()).getPrice(), shoppingCart.getPrice());
        clientDao.deleteClient(client);
        orderDao.deleteOrder(order);
        bookDao.deleteBook(book);
        shoppingCartDao.deleteShoppingCart(shoppingCart);
    }
}
