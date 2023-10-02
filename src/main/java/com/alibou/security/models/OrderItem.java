package com.alibou.security.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    // Связь с товаром
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Связь с заказом
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Cart cart;

    @OneToOne
    private DeliveryMethod deliveryMethod;

    public double getTotalPrice() {
        return (quantity * product.getPrice())+ deliveryMethod.getDeliveryPrice();
    }

}

