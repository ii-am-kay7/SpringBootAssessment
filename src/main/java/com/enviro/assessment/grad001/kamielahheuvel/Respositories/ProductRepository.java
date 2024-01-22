package com.enviro.assessment.grad001.kamielahheuvel.Respositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long Id);

    List<Product> findByName(String name);

    List<Product> findByType(String type);

    List<Product> findByCurrentBalance(BigDecimal balance);
}