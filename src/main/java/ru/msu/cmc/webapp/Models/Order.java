package ru.msu.cmc.webapp.Models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "order_id")
    private int order_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    //@ToString.Exclude
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return this.order_id == order.order_id &&
                this.client_id.getClient_id() == (order.client_id.getClient_id()) &&
                this.order_time.equals(order.order_time) &&
                this.delivery_time.equals(order.delivery_time) &&
                this.status.equals(order.status) &&
                Double.compare(order.order_price, this.order_price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, client_id, order_time, delivery_time, order_price, status);
    }
}
