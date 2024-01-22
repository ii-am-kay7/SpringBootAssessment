package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is the ProductController!";
    }

    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws Exception {
        Product products = productService.getProductsById(productId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String productName) throws Exception {
        List<Product> products = productService.getProductByName(productName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/type/{productType}")
    public ResponseEntity<List<Product>> getProductByType(@PathVariable String productType) throws Exception {
        List<Product> products = productService.getProductsByType(productType);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/currentBalance/{productCurrentBalance}")
    public ResponseEntity<List<Product>> getProductsByCurrentBalance(@PathVariable BigDecimal productCurrentBalance) throws Exception {
        List<Product> products = productService.getProductsByCurrentBalance(productCurrentBalance);
        return ResponseEntity.ok(products);
    }
}