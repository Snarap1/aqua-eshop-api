package com.alibou.security.services;

import com.alibou.security.models.DeliveryMethod;
import com.alibou.security.models.OrderItem;
import com.alibou.security.repositories.OrderItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private  final OrderItemRepo orderItemRepo;

    @Autowired
    public OrderItemService(OrderItemRepo orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }


    // CRUD

    // Create
    public  void saveItem(OrderItem orderItem)
    {
        orderItemRepo.save(orderItem);
    }

    // Read
    public List<OrderItem> getAllItems(){
        return orderItemRepo.findAll();
    }

    public OrderItem getItem(Long item_id){
        return orderItemRepo.findById(item_id);
    }

    // Update
    public void editItem(OrderItem orderItem,Long item_id)
    {
        OrderItem itemForSave = orderItemRepo.findById(item_id);
        // todo add edit there
    }
    @Transactional
    public void updateQuantity(Long orderItemId, int newQuantity) {
        OrderItem orderItem = orderItemRepo.findById(orderItemId);
        orderItem.setQuantity(newQuantity);
        orderItemRepo.save(orderItem);
    }

    @Transactional
    public  void changeDeliveryMethod(Long orderItemId, DeliveryMethod deliveryMethod)
    {
        OrderItem orderItem = orderItemRepo.findById(orderItemId);
        orderItem.setDeliveryMethod(deliveryMethod);
        orderItemRepo.save(orderItem);
    }


    // Delete
    public  void  deleteItem(Long id){
        OrderItem orderItem = orderItemRepo.findById(id);
        orderItemRepo.delete(orderItem);
    }
}
