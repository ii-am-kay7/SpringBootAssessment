package com.enviro.assessment.grad001.kamielahheuvel.Respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByInvestor(Long investorId);

    List<Product> findByName(String name);

    List<Product> findByType(String type);

    List<Product> findByBalanceLessThanEqual(double maxPrice);

    List<Product> findByBalanceBetween(int firstValue, int secValue);

    List<Product> findByInvestorAndType(Long investorId, String type);


}