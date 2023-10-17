package com.alibou.security.services;

import com.alibou.security.models.OrderItem;
import com.alibou.security.repositories.OrderItemRepo;
import jakarta.persistence.EntityNotFoundException;
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
    public void createItem(OrderItem orderItem)
    {
        orderItemRepo.save(orderItem);
    }

    // Read
    public List<OrderItem> getAllItems(){
        return orderItemRepo.findAll();
    }

    public OrderItem getItem(Long item_id){
        return orderItemRepo.findById(item_id)
                .orElseThrow(()->new EntityNotFoundException("Item not found"));
    }

    // Update
    public void editItem(OrderItem orderItem,Long item_id)
    {
        OrderItem itemForSave = getItem(item_id);
        // todo add edit there
    }
    @Transactional
    public void updateQuantity(Long orderItemId, int newQuantity) {
        OrderItem orderItem = getItem(orderItemId);
        orderItem.setQuantity(newQuantity);
        orderItemRepo.save(orderItem);
    }


    // Delete
    public  void  deleteItem(Long itemId){
        OrderItem orderItem = getItem(itemId);
        orderItemRepo.delete(orderItem);
    }

    public void deleteItemByClass(OrderItem orderItem){
        orderItemRepo.delete(orderItem);
    }
}
