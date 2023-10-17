package com.alibou.security.controllers;

import com.alibou.security.enums.DeliveryMethods;
import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.Order;
import com.alibou.security.models.OrderItem;
import com.alibou.security.models.Product;
import com.alibou.security.services.CartService;
import com.alibou.security.services.OrderItemService;
import com.alibou.security.services.OrderService;
import com.alibou.security.services.ProductService;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private  final ProductService productService;
    private  final OrderItemService orderItemService;
    private  final CartService cartService;

    private final OrderService  orderService;

    @Autowired
    public OrderController(ProductService productService, OrderItemService orderItemService, CartService cartService, OrderService orderService) {
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
        this.orderService = orderService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrderByUser(@AuthenticationPrincipal User user){
        return  ResponseEntity.ok(orderService.getOrdersByUser(user));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Order>> getOrderByStatus(OrderStatus orderStatus){
        return ResponseEntity.ok(orderService.getOrdersByStatus(orderStatus));
    }

    @PatchMapping("/status/{orderId}")
    public ResponseEntity<String> changeStatus(@PathVariable Long orderId,OrderStatus status){
        try {
            Order order = orderService.getOrder(orderId);
            order.setStatus(status);
            orderService.saveOrder(order);
            return ResponseEntity.ok("Status changed");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }  catch (Exception e){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> addToCart(@AuthenticationPrincipal User user, @PathVariable long productId){
        try {
            Product product = productService.getProduct(productId);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(1);
            orderItem.setCart(user.getCart());
            user.getCart().getOrderItems().add(orderItem);
            orderItemService.createItem(orderItem);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }

        return ResponseEntity.ok("Product added to cart");
    }

    @PutMapping("/{orderItemId}/quantity")
    public ResponseEntity<String> updateQuantity(@PathVariable Long orderItemId, @RequestParam int newQuantity) {
            orderItemService.updateQuantity(orderItemId, newQuantity);
            return ResponseEntity.ok("Quantity changed");
    }

    @PutMapping("/delivery")
    public ResponseEntity<String> updateDeliveryMethod(@AuthenticationPrincipal User user, @RequestParam DeliveryMethods deliveryMethod) {
        cartService.changeDeliveryMethod(user,deliveryMethod);
        return ResponseEntity.ok("Delivery method changed");
    }

}


