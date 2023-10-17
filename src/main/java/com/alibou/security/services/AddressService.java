package com.alibou.security.services;

import com.alibou.security.models.Address;
import com.alibou.security.repositories.AddressRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private  final AddressRepo addressRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public  void saveAddress(Address address){
        addressRepo.save(address);
    }

    public  void deleteAddress(Address address){
        addressRepo.delete(address);
    }

    public  Address getAddressById(Long id){
        return  addressRepo.findById(id).orElseThrow(()-> new EntityNotFoundException("Address with this id not found"));
    }

    public List<Address> getAllAddresses(){
        return addressRepo.findAll();
    }

    public  void  editAddress(Address addressForSave, Address newAddress){

        addressForSave.setCity(newAddress.getCity());
        addressForSave.setStreet(newAddress.getStreet());
        addressForSave. setEntrance(newAddress.getEntrance());
        addressForSave.setFloor(newAddress.getFloor());
        addressForSave.setAppartment(newAddress.getAppartment());
        addressForSave.setDate(newAddress.getDate());
        addressForSave.setTime(newAddress.getTime());

        addressRepo.save(addressForSave);

    }

}
