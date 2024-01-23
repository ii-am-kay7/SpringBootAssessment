package com.enviro.assessment.grad001.kamielahheuvel.Respositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Method to find product by id
    Optional<Product> findById(Long Id);

    // Method to find products by investor id
    List<Product> findByInvestorId(Long investorId);

    // Method to find products by name 
    List<Product> findByName(String name);

    // Method to find products by type
    List<Product> findByType(String type);

    // Method by find product by currentBalance 
    List<Product> findByCurrentBalance(BigDecimal balance);

    // Method to find product by type and name
    Product findByTypeAndName(String string, String string2);
}