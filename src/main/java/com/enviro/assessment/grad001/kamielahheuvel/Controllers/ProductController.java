package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Services.InvestorService;
import com.enviro.assessment.grad001.kamielahheuvel.Services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired InvestorService investorService;

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

    // Endpoint to get products by investor ID
    @GetMapping("/investorId/{investorId}")
    public ModelAndView showInvestorProducts(@PathVariable Long investorId) {
        ModelAndView modelAndView = new ModelAndView();
    
        List<Product> productList = productService.getProductsByInvestorId(investorId);
    
        modelAndView.addObject("productList", productList);
        modelAndView.addObject("investorId", investorId);
        modelAndView.setViewName("products");
    
        return modelAndView;
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

   // Endpoint to show the new product form
    @GetMapping("/{investorId}/new_product")
    public ModelAndView showNewProductForm(@PathVariable Long investorId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("investorId", investorId);
        modelAndView.setViewName("new_product");
        return modelAndView;
    }

   // Endpoint to create a new product
    @PostMapping("/{investorId}/new_product.action")
    public ResponseEntity<Product> createNewProduct(
            @PathVariable Long investorId,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam BigDecimal currentBalance) throws Exception {

        try {
            // Create a new instance of Product using the provided request parameters
            Product newProduct = new Product(type, name, currentBalance);

            // Set the investorId for the newly created product
            Investor investor = investorService.getInvestorById(investorId);
            newProduct.setInvestor(investor);

            // Call the service method to create the new product
            Product createdProduct = productService.createNewProduct(newProduct);

            // Return the created product in the response
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (AppExceptions e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint to show the investor's product list
    @GetMapping("/{investorId}/list")
    public ModelAndView showInvestorProductList(@PathVariable Long investorId) {
        ModelAndView modelAndView = new ModelAndView();

        List<Product> productList = productService.getProductsByInvestorId(investorId);

        modelAndView.addObject("productList", productList);
        modelAndView.addObject("investorId", investorId);
        modelAndView.setViewName("products");

        return modelAndView;
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