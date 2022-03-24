package ru.msu.cmc.webapp.Dao;
import ru.msu.cmc.webapp.Models.Order;
import ru.msu.cmc.webapp.Models.ShoppingCart;

import java.util.List;

public interface ShoppingCartDao {
    void createShoppingCart(ShoppingCart shoppingCart);
    void updateShoppingCart(ShoppingCart shoppingCart);
    void deleteShoppingCart(ShoppingCart shoppingCart);
    ShoppingCart getShoppingCartById(int id);
    List<ShoppingCart> getShoppingCartsByOrderId(Order order);
    double getShoppingCartPrice(ShoppingCart shoppingCart);
}
