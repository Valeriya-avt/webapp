package ru.msu.cmc.webapp.Models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "shoppingcart")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "shopping_cart_id")
    private int shopping_cart_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    @NonNull
    private Order order_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    @NonNull
    private Book book_id;

    @Column(nullable = false, name = "price")
    private double price;

    @Column(nullable = false, name = "amount")
    private int amount;

    public ShoppingCart(Order order, Book book, int amount) {
        this.order_id = order;
        this.book_id = book;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShoppingCart shoppingCart = (ShoppingCart) o;
        return this.shopping_cart_id == shoppingCart.shopping_cart_id &&
                this.order_id.getOrder_id() == (shoppingCart.order_id.getOrder_id()) &&
                this.book_id.getBook_id() == (shoppingCart.book_id.getBook_id()) &&
                Integer.compare(shoppingCart.amount, this.amount) == 0 &&
                Double.compare(shoppingCart.price, this.price) == 0;
    }
}
