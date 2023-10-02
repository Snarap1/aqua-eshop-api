package com.alibou.security.repositories;

import com.alibou.security.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Integer> {
    OrderItem findById(Long id);

}
