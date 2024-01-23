package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.math.BigDecimal;
import java.util.List;

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

    // Hello endpoint for testing
    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is the ProductController!";
    }

    // Endpoint to get a product by ID
    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws Exception {
        Product products = productService.getProductsById(productId);
        return ResponseEntity.ok(products);
    }

    // Endpoint to get products associated with a specific investor ID
    @GetMapping("/investorId/{investorId}")
    public ResponseEntity<List<Product>> getProductByInvestorId(@PathVariable Long investorId) throws Exception {
        List<Product> products = productService.getProductsByInvestorId(investorId);
        return ResponseEntity.ok(products);
    }

    // Endpoint to get products by name
    @GetMapping("/name/{productName}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String productName) throws Exception {
        List<Product> products = productService.getProductByName(productName);
        return ResponseEntity.ok(products);
    }

    // Endpoint to get products by type
    @GetMapping("/type/{productType}")
    public ResponseEntity<List<Product>> getProductByType(@PathVariable String productType) throws Exception {
        List<Product> products = productService.getProductsByType(productType);
        return ResponseEntity.ok(products);
    }

    // Endpoint to get products by current balance
    @GetMapping("/currentBalance/{productCurrentBalance}")
    public ResponseEntity<List<Product>> getProductsByCurrentBalance(@PathVariable BigDecimal productCurrentBalance) throws Exception {
        List<Product> products = productService.getProductsByCurrentBalance(productCurrentBalance);
        return ResponseEntity.ok(products);
    }

    // Endpoint to create a new product
    @PostMapping("/new_product")
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) throws Exception {
        Product createdProduct = productService.createNewProduct(newProduct);
        return ResponseEntity.ok(createdProduct);
    }

    // Endpoint to update an existing product by ID
    @PutMapping("/update/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) throws Exception {
        productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to delete a product by ID
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) throws Exception {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}