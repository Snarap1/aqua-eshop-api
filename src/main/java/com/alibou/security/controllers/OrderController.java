package com.alibou.security.controllers;

import com.alibou.security.models.DeliveryMethod;
import com.alibou.security.models.OrderItem;
import com.alibou.security.models.Product;
import com.alibou.security.services.OrderItemService;
import com.alibou.security.services.ProductService;
import com.alibou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private  final ProductService productService;
    private  final OrderItemService orderItemService;

    @Autowired
    public OrderController(ProductService productService, OrderItemService orderItemService) {
        this.productService = productService;
        this.orderItemService = orderItemService;
    }


    @PostMapping("")
    public ResponseEntity<String> addToCart(@AuthenticationPrincipal User user, @RequestBody long productId){
        OrderItem orderItem = new OrderItem();
        Product product = productService.getProduct(productId);
        orderItem.setProduct(product);
        orderItem.setQuantity(1);
        orderItem.setCart(user.getCart());
        orderItemService.createItem(orderItem);
        return ResponseEntity.ok("Product added to cart");
    }

    @PutMapping("/{orderItemId}/quantity")
    public ResponseEntity<String> updateQuantity(@PathVariable Long orderItemId, @RequestParam int newQuantity) {
            orderItemService.updateQuantity(orderItemId, newQuantity);
            return ResponseEntity.ok("Quantity changed");
    }

    @PutMapping("/{orderItemId}/delivery")
    public ResponseEntity<String> updateDeliveryMethod(@PathVariable Long orderItemId, @RequestParam DeliveryMethod deliveryMethod) {
        orderItemService.changeDeliveryMethod(orderItemId,deliveryMethod);
        return ResponseEntity.ok("Delivery method changed");
    }

}



