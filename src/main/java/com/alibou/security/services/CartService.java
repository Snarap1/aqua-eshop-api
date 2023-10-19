package com.alibou.security.services;

import com.alibou.security.enums.DeliveryMethods;
import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.Cart;
import com.alibou.security.models.Order;
import com.alibou.security.repositories.CartRepo;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public  Cart saveCart(Cart cart)
    {
      return   cartRepo.save(cart);
    }

    // Read
    public List<Cart> getAllCarts(){
        return cartRepo.findAll();
    }

    @Transactional
    public Cart getCart(Long cart_id){
        return cartRepo.findById(cart_id)
                .orElseThrow(()-> new EntityNotFoundException("Cart for this ID not found"));
    }

    public  Cart getCartByUser(User user){
        return cartRepo.getCartByUser(user);
    }

    // Update
    public void editCart(Cart cart,Long cart_id)
    {
        Cart cartForSave = getCart(cart_id);
        // todo add edit there
    }

    // Delete
    public  void  deleteCart(Long cartId){
        Cart cart = getCart(cartId);
        cartRepo.delete(cart);
    }

    @Transactional
    public  void changeDeliveryMethod(User user, DeliveryMethods deliveryMethod)
    {
        Cart cart = user.getCart();
        cart.setDeliveryMethod(deliveryMethod);
        cartRepo.save(cart);
    }



}
