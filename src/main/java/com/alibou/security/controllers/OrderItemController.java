package com.alibou.security.controllers;


import com.alibou.security.models.OrderItem;
import com.alibou.security.services.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
public class OrderItemController {
    private  final OrderItemService orderItemService;


    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/")
    public ResponseEntity<OrderItem>   createItem (@RequestBody OrderItem orderItem){
      orderItemService.createItem(orderItem);
      return   ResponseEntity.ok(orderItem);
    }

    @GetMapping("/all")
    public ResponseEntity< List<OrderItem>> getAllItems(){
        List<OrderItem> orderItems = orderItemService.getAllItems();
        return  ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?>  getItem(@PathVariable Long itemId){
        try {
            OrderItem orderItem =  orderItemService.getItem(itemId);
            return ResponseEntity.ok(orderItem);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }

    }

    @DeleteMapping("/{itemID}")
    public  ResponseEntity<String> deleteItem(@PathVariable Long itemID){
        try {
            orderItemService.deleteItem(itemID);
            return ResponseEntity.ok("Item deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }



}
