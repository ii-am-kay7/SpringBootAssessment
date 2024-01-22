package com.enviro.assessment.grad001.kamielahheuvel.Respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;

public interface InvestorRepository extends JpaRepository<Investor, Long>{

    // Method to find investor by name
    Investor findByName(String name);

    // Method to find investor by name along with associated products
    @Query("SELECT i FROM Investor i LEFT JOIN FETCH i.investments WHERE i.name = :name")
    Optional<Investor> findInvestorWithProductsByName(String name);
     
}
