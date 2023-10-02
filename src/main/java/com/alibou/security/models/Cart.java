package com.alibou.security.models;
import com.alibou.security.user.User;
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

    private Date orderDate;
    private double totalCost;

    // Связь с товарами в заказе
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems ;//= new ArrayList<>();

    @OneToOne
    private User user;
    public double calculateTotalCost() {
        double cost = 0.0;
        for (OrderItem item : orderItems) {
            cost += item.getTotalPrice();
        }
        totalCost = cost;
        return totalCost;
    }
}
