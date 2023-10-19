package com.alibou.security.services;

import com.alibou.security.enums.OrderStatus;
import com.alibou.security.models.Cart;
import com.alibou.security.models.Order;
import com.alibou.security.repositories.OrderRepo;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private  final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }


    // CRUD

    // Create
    public void saveOrder(Order order)
    {
        orderRepo.save(order);
    }

    // Read
    public List<Order> getAllOrders(){
        return orderRepo.findAll();
    }

    public  List<Order> getOrdersByUser(User user){
        return orderRepo.findByUser(user);
    }

    public  List<Order> getOrdersByUserAndStatus(User user,OrderStatus status){
        return orderRepo.findByUserAndStatus(user,status);
    }


    public  List<Order> getOrdersByStatus(OrderStatus status){
        return orderRepo.findByStatus(status);
    }


    public Order getOrder(Long orderId){
        return orderRepo.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order for this ID not found"));
    }

//    public  Cart getCartByUser(User user){
//        return  cartRepo.getCartByUser(user);
//    }

    // Update

    public void editOrderStatus(Order order,OrderStatus status)
    {
        order.setStatus(status);
        orderRepo.save(order);
    }

    // Delete
    public  void  deleteOrder(Long orderId){
        Order order = getOrder(orderId);
        orderRepo.delete(order);
    }
}
