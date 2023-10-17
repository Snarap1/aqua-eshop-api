package com.alibou.security.models;

import com.alibou.security.enums.DeliveryMethods;
import com.alibou.security.enums.OrderStatus;
import com.alibou.security.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OrderModel")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<OrderItem> orderItems; // Список товаров в заказе

    private double totalAmount; // Общая стоимость заказа

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToOne
    private  Address address;

    private OrderStatus status; // Статус заказа (в обработке, доставляется, доставлен и т. д.)

    private DeliveryMethods deliveryMethod;
    // Другие свойства заказа и методы
}
