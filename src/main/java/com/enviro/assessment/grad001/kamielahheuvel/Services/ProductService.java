package com.enviro.assessment.grad001.kamielahheuvel.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

     // This method creates a new product, validates its details, and saves it in a transactional manner.
     @Transactional
     public Product createNewProduct(Product newProduct) throws AppExceptions {
         // Validate product details before creating
         validateProductDetails(newProduct);
 
         // Save the new product
         return productRepository.save(newProduct);
     }
 
     // This method performs various validations on the details of a product.
     private void validateProductDetails(Product product) throws AppExceptions {
         // Validate that type is either savings or retirement
         String productType = product.getType();
         if (!"SAVINGS".equalsIgnoreCase(productType) && !"RETIREMENT".equalsIgnoreCase(productType)) {
             throw new AppExceptions("Invalid product type. Type must be either SAVINGS or RETIREMENT.");
         }
 
         // Validate that current balance is greater than 0
         BigDecimal currentBalance = product.getCurrentBalance();
         if (currentBalance == null || currentBalance.compareTo(BigDecimal.ZERO) <= 0) {
             throw new AppExceptions("Current balance must be greater than 0.");
         }
 
         // Validate that investor's age is greater than 65 for retirement
         if ("RETIREMENT".equalsIgnoreCase(productType) && product.getInvestor().getAge() <= 65) {
             throw new AppExceptions("Investor's age must be greater than 65 for RETIREMENT products.");
         }
 
         // Validate that the name of the product is not empty
         String productName = product.getName();
         if (productName == null || productName.trim().isEmpty()) {
             throw new AppExceptions("Product name cannot be empty.");
         }
     }
 
     // These methods retrieve product information based on different criteria.
 
     // This method retrieves a product by its ID.
     public Product getProductsById(Long Id) throws AppExceptions {
         Optional<Product> OptionalProducts = productRepository.findById(Id);
         if (OptionalProducts.isPresent() && OptionalProducts != null) {
             return OptionalProducts.get();
         } else {
             throw new AppExceptions("Products by" + Id + " not found");
         }
     }
 
     // This method retrieves products associated with a specific investor ID.
     public List<Product> getProductsByInvestorId(Long investorId) throws AppExceptions {
         List<Product> OptionalProducts = productRepository.findByInvestorId(investorId);
         if (OptionalProducts != null) {
             return OptionalProducts;
         } else {
             throw new AppExceptions("Products by" + investorId + " not found");
         }
     }
 
     // This method retrieves products by their name.
     public List<Product> getProductByName(String name) throws AppExceptions {
         List<Product> products = productRepository.findByName(name);
         if (!products.isEmpty() && products != null) {
             return products;
         } else {
             return new ArrayList<Product>();
         }
     }
 
     // This method retrieves products by their type.
     public List<Product> getProductsByType(String Type) throws AppExceptions {
         List<Product> products = productRepository.findByType(Type);
         if (!products.isEmpty() && products != null) {
             return products;
         } else {
             return new ArrayList<Product>();
         }
     }
 
     // This method retrieves products by their current balance.
     public List<Product> getProductsByCurrentBalance(BigDecimal balance) throws AppExceptions {
         List<Product> products = productRepository.findByCurrentBalance(balance);
         if (!products.isEmpty() && products != null) {
             return products;
         } else {
             return new ArrayList<Product>();
         }
     }

       // Update an existing product
    @Transactional
    public void updateProduct(Long id, Product updatedProduct) throws AppExceptions {
        Optional<Product> optionalExistingProduct = productRepository.findById(id);

        if (optionalExistingProduct.isPresent()) {
            Product existingProduct = optionalExistingProduct.get();

            // Update fields of the existing product with the values from the updated product
            existingProduct.setType(updatedProduct.getType());
            existingProduct.setCurrentBalance(updatedProduct.getCurrentBalance());
            existingProduct.setName(updatedProduct.getName());

            // Save the updated product
            productRepository.save(existingProduct);
        } else {
            throw new AppExceptions("Product with ID " + id + " not found");
        }
    }

    // Delete a product by ID
    @Transactional
    public void deleteProduct(Long id) throws AppExceptions {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        } else {
            throw new AppExceptions("Product with ID " + id + " not found");
        }
    }

}