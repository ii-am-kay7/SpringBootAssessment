package com.enviro.assessment.grad001.kamielahheuvel.Models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;
    private BigDecimal currentBalance;

    // Adding relationship to Investor
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "investor_id")
    @JsonBackReference
    private Investor investor;

    // Default constructor required by JPA
    public Product(){}

    // Parameterized constructor for creating an Investor instance with data
    public Product(String Type, String Name, BigDecimal CurrentBalance){
        this.type = Type;
        this.name = Name;
        this.currentBalance = CurrentBalance;
    }

    // Getter and Setter methods
    public Long getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public String getName(){
        return name;
    }

    public Investor getInvestor(){
        return investor;
    }

    public BigDecimal getCurrentBalance(){
        return currentBalance;
    }

    public Long getInvestorId(){
        return investor.getId();
    }

    public void setId(Long Id){
        this.id = Id;
    }

    public void setType(String Type){
        this.type = Type;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setCurrentBalance(BigDecimal CurrentBalance){
        this.currentBalance = CurrentBalance;
    }

    public void setInvestor(Investor investor){
        this.investor = investor;
    }

    public void setInvestorId(Long investorId) {
        this.investor.setId(investorId);
    }

    // Returns a string presentation of the product
    @Override
    public String toString(){
        return "Product[Id: " + id  + ", Type: " + type + ", Name: " + name +
        ", CurrentBalance: " + currentBalance + "]";
    }
}