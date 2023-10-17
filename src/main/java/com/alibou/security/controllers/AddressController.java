package com.alibou.security.controllers;

import com.alibou.security.models.Address;
import com.alibou.security.models.Order;
import com.alibou.security.services.AddressService;
import com.alibou.security.services.OrderService;
import com.alibou.security.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {

    private  final AddressService addressService;
    private  final OrderService orderService;

    @Autowired
    public AddressController(AddressService addressService, OrderService orderService) {
        this.addressService = addressService;
        this.orderService = orderService;
    }


    @PostMapping("/")
    public ResponseEntity<Address> addAddress(@RequestBody Address address, @AuthenticationPrincipal User user)
    {
        List<Order> orders= user.getOrders();
        Order order = orders.get(orders.size()-1);
        address.setOrder(order);
        order.setAddress(address);
        addressService.saveAddress(address);
        orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses(){
        List<Address> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable Long addressId)
    {
        try {
            Address address = addressService.getAddressById(addressId);
            return ResponseEntity.ok(address);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId)
    {
        try {
            Address address = addressService.getAddressById(addressId);
            addressService.deleteAddress(address);
            return ResponseEntity.ok("Address removed");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @PatchMapping("/{orderId}")
    public  ResponseEntity<?> editAddress(@PathVariable Long orderId, @RequestBody Address address)
    {
        try {
            Order order = orderService.getOrder(orderId);
            Address addressForSave = order.getAddress();
            addressService.editAddress(addressForSave,address);
            return  ResponseEntity.ok("Address edited");
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e)
        {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }


    }

}

