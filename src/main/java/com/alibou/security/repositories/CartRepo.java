package com.alibou.security.repositories;

import com.alibou.security.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {

    Optional<Cart> findById(Long id);
    Cart getCartById(Long id);
}
