package com.alibou.security.repositories;

import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.Cart;
import com.alibou.security.models.Order;
import com.alibou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {
    Optional<Order> findById(Long id);

    List<Order> findByUser(User user);

    List<Order> findByStatus(OrderStatus status);

}
