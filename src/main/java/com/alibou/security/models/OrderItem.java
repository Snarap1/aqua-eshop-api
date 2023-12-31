package com.alibou.security.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JsonIgnore
    private  Order order;


    public double getTotalPrice() {
        return (quantity * product.getPrice());
    }

}

