package com.alibou.security.services;

import com.alibou.security.models.OrderItem;
import com.alibou.security.models.Product;
import com.alibou.security.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private  final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    // CRUD

    // Create
    public  void saveProduct(Product product)
    {
        productRepo.save(product);
    }

    // Read
    public List<Product> getAllProducts(){
        return productRepo.findAll();
    }

    public Product getProduct(Long product_id){
        return productRepo.findById(product_id);
    }

    // Update
    public void editProduct(Product product,Long product_id)
    {
        Product productForSave = productRepo.findById(product_id);
        productForSave.setName(product.getName());
        productForSave.setDescription(product.getDescription());
        productForSave.setPrice(product.getPrice());
    }

    // Delete
    public  void  deleteProduct(Long product_id){
        Product product = productRepo.findById(product_id);
        productRepo.delete(product);
    }

}
