package com.enviro.assessment.grad001.kamielahheuvel.Respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Models.WithdrawalNotice;

public interface WithdrawalNoticeRepository extends JpaRepository<WithdrawalNotice, Long>{

    // Method to find withdrawal notice by Product
    List<WithdrawalNotice> findByProductId(Product productId);

    // Method to find notice by id
    Optional<WithdrawalNotice> findById(Long Id); 
      
}