package com.alibou.security.repositories;

import com.alibou.security.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    Product findById(Long product_Id);

}
