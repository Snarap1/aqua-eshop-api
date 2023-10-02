package com.alibou.security.controllers;

import com.alibou.security.models.DeliveryMethod;
import com.alibou.security.services.DeliveryMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/method")
public class DeliveryMethodController {
    private  final DeliveryMethodService deliveryMethodService;


    @Autowired
    public DeliveryMethodController(DeliveryMethodService deliveryMethodService) {
        this.deliveryMethodService = deliveryMethodService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDelivMethod(@RequestBody DeliveryMethod deliveryMethod){
        deliveryMethodService.saveDM(deliveryMethod);
        return ResponseEntity.ok("Delivery method was added");
    }

    @GetMapping("/all")
    public List<DeliveryMethod> getAllDelivMethods(){
        return  deliveryMethodService.getAllMethods();
    }

    @GetMapping("/{methodId}")
    public DeliveryMethod getDelivMethod(@PathVariable Long methodId)
    {
        return deliveryMethodService.getMethod(methodId);
    }

    @PatchMapping("/{methodId}")
    public ResponseEntity<String> editDelivMethod(@PathVariable Long methodId, @RequestBody DeliveryMethod deliveryMethod){
        deliveryMethodService.editDM(deliveryMethod,methodId);
        return  ResponseEntity.ok("Method edited");
    }

    @DeleteMapping("/{methodId}")
    public  ResponseEntity<String> deleteDelivMethod(@PathVariable Long methodId)
    {
        deliveryMethodService.deleteDM(methodId);
        return  ResponseEntity.ok("Method deleted");
    }
}
