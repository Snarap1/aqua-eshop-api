package com.alibou.security.controllers;

import com.alibou.security.models.Cart;
import com.alibou.security.models.DeliveryMethod;
import com.alibou.security.models.OrderItem;
import com.alibou.security.models.Product;
import com.alibou.security.services.CartService;
import com.alibou.security.services.DeliveryMethodService;
import com.alibou.security.services.OrderItemService;
import com.alibou.security.services.ProductService;
import com.alibou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private  final CartService cartService;
    private final DeliveryMethodService methodService;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, DeliveryMethodService methodService, OrderItemService orderItemService, ProductService productService) {
        this.cartService = cartService;
        this.methodService = methodService;
        this.orderItemService = orderItemService;
        this.productService = productService;
    }
//    @PostMapping("")
//    public ResponseEntity<String> addToCart(@AuthenticationPrincipal User user, @RequestBody long productId){
//        OrderItem orderItem = new OrderItem();
//        Product product = productService.getProduct(productId);
//        orderItem.setProduct(product);
//        orderItem.setQuantity(1);
//        orderItem.setCart(user.getCart());
//        orderItemService.saveItem(orderItem);
//        return ResponseEntity.ok("Product added to cart");
//    }
//
//    @PutMapping("/{orderItemId}/quantity")
//    public ResponseEntity<String> updateQuantity(@PathVariable Long orderItemId, @RequestParam int newQuantity) {
//            orderItemService.updateQuantity(orderItemId, newQuantity);
//            return ResponseEntity.ok("Количество товаров в заказе успешно обновлено.");
//    }
//
//    @PutMapping("/{orderItemId}/delivery")
//    public ResponseEntity<String> updateDeliveryMethod(@PathVariable Long orderItemId, @RequestParam DeliveryMethod deliveryMethod) {
//        orderItemService.changeDeliveryMethod(orderItemId,deliveryMethod);
//        return ResponseEntity.ok("Способ доставки изменён");
//    }


    @GetMapping("/all")
    public List<Cart> getAllCarts(){
        return cartService.getAllCarts();
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Long cartId){
        return cartService.getCart(cartId);
    }

    @DeleteMapping("/{cartId}")
    public  ResponseEntity<String> deleteCart(@PathVariable Long cartId)
    {
        cartService.deleteCart(cartId);
        return  ResponseEntity.ok("Cart was deleted");
    }



}
