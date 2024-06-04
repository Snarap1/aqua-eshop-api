package com.alibou.security.controllers;

import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.Cart;
import com.alibou.security.models.Order;
import com.alibou.security.models.OrderItem;
import com.alibou.security.services.CartService;
import com.alibou.security.services.OrderItemService;
import com.alibou.security.services.OrderService;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private  final CartService cartService;
    private  final OrderItemService orderItemService;
    private  final OrderService orderService;


    @Autowired
    public CartController(CartService cartService, OrderItemService orderItemService, OrderService orderService) {
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.orderService = orderService;
    }


    @PostMapping
    public  ResponseEntity<Cart> createCart(@RequestBody Cart cart){
        Cart creeatedCart = cartService.saveCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(creeatedCart);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCarts(){
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable Long cartId){
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(cart);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("")
    public ResponseEntity<Cart> getCartByUser(@AuthenticationPrincipal User user){
        Cart cart = cartService.getCartByUser(user);
        double cost = cart.calculateTotalCost();
        cart.setTotalCost(cost);
        cartService.saveCart(cart);
      return ResponseEntity.ok(cart);
    }



    @DeleteMapping("/{cartId}")
    public  ResponseEntity<String> deleteCart(@PathVariable Long cartId)
    {
        try {
            cartService.deleteCart(cartId);
            return  ResponseEntity.ok("Cart was deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }

    }


    @PostMapping("/buy")
    public ResponseEntity<String> buyItems(@AuthenticationPrincipal User user){
        try {
            orderService.buy(user);
            return ResponseEntity.ok("offer done !");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");

        }
    }

}


