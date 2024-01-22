package com.enviro.assessment.grad001.kamielahheuvel.Respositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;

public interface InvestorRepository extends JpaRepository<Investor, Long>{

    // Method to find investor by name
    Investor findByName(String name);

    // Method to find investor by id
    Optional<Investor> findById(Long id);

    // Method to find all investors
    List<Investor> findAll();
}
