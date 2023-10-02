package com.alibou.security.services;

import com.alibou.security.models.DeliveryMethod;
import com.alibou.security.repositories.DeliveryMethodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryMethodService {

    private  final DeliveryMethodRepo deliveryMethodRepo;

    @Autowired
    public DeliveryMethodService(DeliveryMethodRepo deliveryMethodRepo) {
        this.deliveryMethodRepo = deliveryMethodRepo;
    }

    // CRUD

    // Create
    public  void saveDM(DeliveryMethod deliveryMethod)
    {
        deliveryMethodRepo.save(deliveryMethod);
    }

    // Read
    public List<DeliveryMethod> getAllMethods(){
        return deliveryMethodRepo.findAll();
    }

    public DeliveryMethod getMethod(Long method_id){
        return deliveryMethodRepo.findByDeliveryId(method_id);
    }

    // Update
    public void editDM(DeliveryMethod deliveryMethod,Long method_id)
    {
        DeliveryMethod deliveryMethodSave = deliveryMethodRepo.findByDeliveryId(method_id);
        deliveryMethodSave.setDeliveryName(deliveryMethod.getDeliveryName());
        deliveryMethodSave.setDeliveryPrice(deliveryMethod.getDeliveryPrice());
    }

    // Delete
    public  void  deleteDM(Long id){
        DeliveryMethod deliveryMethod = deliveryMethodRepo.findByDeliveryId(id);
        deliveryMethodRepo.delete(deliveryMethod);
    }

}
