package ru.msu.cmc.webapp.Models;

import lombok.*;
import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "order_id")
    private int order_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @NonNull
    private Client client_id;

    @Column(nullable = false, name = "order_time")
    @NonNull
    private Date order_time;

    @Column(nullable = false, name = "delivery_time")
    @NonNull
    private Date delivery_time;

    @Column(nullable = false, name = "order_price")
    private double order_price;

    @Column(nullable = false, name = "status")
    @NonNull
    private String status;
}
