package com.alibou.security.config;

import com.alibou.security.repositories.*;
import com.alibou.security.services.*;
import com.alibou.security.user.UserRepository;
import com.alibou.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration

public class TestConfig {

    private final OrderRepo orderRepo;
    private  final ProductRepo productRepo;
    private  final AddressRepo addressRepo;
    private  final CartRepo cartRepo;
    private final DeliveryMethodRepo deliveryMethodRepo;
    private final OrderItemRepo orderItemRepo;

    @Autowired
    public TestConfig(OrderRepo orderRepo, ProductRepo productRepo, AddressRepo addressRepo, CartRepo cartRepo, DeliveryMethodRepo deliveryMethodRepo, OrderItemRepo orderItemRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.addressRepo = addressRepo;
        this.cartRepo = cartRepo;
        this.deliveryMethodRepo = deliveryMethodRepo;
        this.orderItemRepo = orderItemRepo;
    }


    @Bean
    @Primary
    public ProductService productService(){
        return new ProductService(productRepo);
    }

    @Bean
    @Primary
    public AddressService addressService(){
        return  new AddressService(addressRepo);
    }

    @Bean
    @Primary
    public CartService cartService(){
        return new CartService(cartRepo);
    }

    @Bean
    @Primary
    public  DeliveryMethodService deliveryMethodService(){
        return  new DeliveryMethodService(deliveryMethodRepo);
    }

    @Bean
    @Primary
    public OrderItemService orderItemService(){
        return new OrderItemService(orderItemRepo);
    }

    @Bean
    @Primary
    public OrderService orderService(){
        return  new OrderService(orderRepo,orderItemService(), productService(),addressService(),cartService());
    }


}
