package com.alibou.security.controllers;

import com.alibou.security.models.Product;
import com.alibou.security.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private  final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> addProduct(@RequestBody Product product)
    {
        productService.saveProduct(product);
        return ResponseEntity.ok("Product saved");
    }

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public  Product getProduct(@PathVariable Long productId)
    {
        return productService.getProduct(productId);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> editProduct(@PathVariable Long productId, @RequestBody Product product){
        productService.editProduct(product,productId);
      return   ResponseEntity.ok("Product changed");
    }

    @DeleteMapping("{productId}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted");
    }





}
