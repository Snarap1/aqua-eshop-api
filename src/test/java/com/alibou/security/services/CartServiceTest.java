package com.alibou.security.services;

import com.alibou.security.models.Cart;
import com.alibou.security.repositories.CartRepo;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartRepo cartRepo;

    @InjectMocks
    private  CartService cartService;



    @Test
    void saveCart() {
        Cart cart = new Cart();
        cartService.saveCart(cart);
        verify(cartRepo,times(1)).save(cart);
    }

    @Test
    void getCart() {

        Long id = 1L;
        Cart cart = new Cart();
        cart.setId(id);

        when(cartRepo.findById(id)).thenReturn(Optional.of(cart));


        Cart res = cartService.getCart(id);
        assertEquals(cart,res);
    }
    @Test
    void getCart_wrong() {

        Long id = 1L;
        when(cartRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()-> cartService.getCart(id) );
    }

    @Test
    void getCartByUser() {

        Cart cart = new Cart();
        User user = User.builder().id(1).cart(cart).build();
        cart.setUser(user);

        when(cartRepo.getCartByUser(user)).thenReturn(cart);

        var res =    cartService.getCartByUser(user);

        assertEquals(cart,res);
    }
}