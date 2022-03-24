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
}
