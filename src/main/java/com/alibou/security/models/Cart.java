package com.alibou.security.models;
import com.alibou.security.enums.DeliveryMethods;
import com.alibou.security.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalCost;

    // Связь с товарами в заказе
    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<OrderItem> orderItems; //= new ArrayList<>();

    @OneToOne
    @JsonIgnore
    private User user;

    private DeliveryMethods deliveryMethod = DeliveryMethods.DELIVERY;

      public double calculateTotalCost() {
        double cost = 0.0;
        for (OrderItem item : orderItems) {
            cost += item.getTotalPrice();
        }
        totalCost = cost + deliveryMethod.getCost();
        return totalCost;
    }



}


