package com.alibou.security.controllers;

import com.alibou.security.enums.DeliveryMethods;
import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.Address;
import com.alibou.security.models.Order;
import com.alibou.security.models.OrderItem;
import com.alibou.security.models.Product;
import com.alibou.security.services.*;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private  final AddressService addressService;
    private final OrderService  orderService;

    @Autowired
    public OrderController(ProductService productService, OrderItemService orderItemService, CartService cartService, AddressService addressService, OrderService orderService) {
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
        this.addressService = addressService;
        this.orderService = orderService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orderHistory")
    public ResponseEntity<List<Order>> getUserHistory(@AuthenticationPrincipal User user){
        return  ResponseEntity.ok(orderService.getOrdersByUserAndStatus(user,OrderStatus.DELIVERED));
    }

    @GetMapping("/orderList")
    public ResponseEntity<List<Order>> getUsersOrders(@AuthenticationPrincipal User user){
        return  ResponseEntity.ok(orderService.getOrdersByUser(user));
    }


    @GetMapping("/status")
    public ResponseEntity<List<Order>> getOrderByStatus(OrderStatus orderStatus){
        List<Order> orders = orderService.getOrdersByStatus(orderStatus);
       return ResponseEntity.ok(orders);
    }


    @PatchMapping("/status/{orderId}")
    public ResponseEntity<String> changeStatus(@PathVariable Long orderId,OrderStatus status){
        try {
            orderService.changeStatus(orderId,status);
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
        orderService.addToCart(productId,user);
            return ResponseEntity.ok("Product added to cart");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
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

    @PatchMapping("/address/{orderId}")
    public ResponseEntity<String> setAddress(@PathVariable Long orderId, Long addresId){
        try {
            orderService.setAddress(orderId,addresId);
            return ResponseEntity.ok("Address changed");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/downloadOrderDetails/{orderId}")
    public ResponseEntity<?> downloadOrderDetails(@PathVariable Long orderId) {
        try {

            byte[] content = orderService.downloadOrderDetails(orderId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "order_"+orderId+".txt");

            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }
}

