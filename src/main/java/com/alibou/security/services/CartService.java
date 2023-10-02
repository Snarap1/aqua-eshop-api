package com.alibou.security.services;

import com.alibou.security.models.Cart;
import com.alibou.security.models.OrderItem;
import com.alibou.security.repositories.CartRepo;
import com.alibou.security.repositories.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private  final CartRepo cartRepo;

    @Autowired
    public CartService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }


    // CRUD

    // Create
    public  void saveCart(Cart cart)
    {
        cartRepo.save(cart);
    }

    // Read
    public List<Cart> getAllCarts(){
        return cartRepo.findAll();
    }

    public Cart getCart(Long cart_id){
        return cartRepo.findById(cart_id);
    }

    // Update
    public void editCart(Cart cart,Long cart_id)
    {
        Cart cartForSave = cartRepo.findById(cart_id);
        // todo add edit there
    }

    // Delete
    public  void  deleteCart(Long id){
        Cart cart = cartRepo.findById(id);
        cartRepo.delete(cart);
    }



}
