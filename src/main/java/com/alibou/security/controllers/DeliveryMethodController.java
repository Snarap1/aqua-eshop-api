package com.alibou.security.controllers;

import com.alibou.security.models.DeliveryMethod;
import com.alibou.security.services.DeliveryMethodService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/")
    public ResponseEntity<DeliveryMethod> createDelivMethod(@RequestBody DeliveryMethod deliveryMethod){
        return ResponseEntity.status(HttpStatus.CREATED).body(deliveryMethodService.createDM(deliveryMethod));
    }

    @GetMapping("/all")
    public ResponseEntity <List<DeliveryMethod>> getAllDelivMethods(){
        List<DeliveryMethod> deliveryMethods = deliveryMethodService.getAllMethods();
        return  ResponseEntity.ok(deliveryMethods);
    }

    @GetMapping("/{methodId}")
    public ResponseEntity <?> getDelivMethod(@PathVariable Long methodId)
    {
        try {
            DeliveryMethod deliveryMethod = deliveryMethodService.getMethod(methodId);
            return ResponseEntity.ok(deliveryMethod);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery method not found");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PatchMapping("/{methodId}")
    public ResponseEntity<String> editDelivMethod(@PathVariable Long methodId, @RequestBody DeliveryMethod deliveryMethod){
        try {
            deliveryMethodService.editDM(deliveryMethod,methodId);
            return  ResponseEntity.ok("Method edited");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery method not found");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }

    }

    @DeleteMapping("/{methodId}")
    public  ResponseEntity<String> deleteDelivMethod(@PathVariable Long methodId)
    {
        try {
            deliveryMethodService.deleteDM(methodId);
            return  ResponseEntity.ok("Method deleted");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Delivery method not found");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
}

