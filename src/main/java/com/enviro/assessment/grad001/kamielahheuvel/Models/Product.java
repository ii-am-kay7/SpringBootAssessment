package com.enviro.assessment.grad001.kamielahheuvel.Models;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String name;
    private BigDecimal currentBalance;

    // Default constructor required by JPA
    public Product(){}

    // Parameterized constructor for creating an Investor instance with data
    public Product(Long Id, String Type, String Name, BigDecimal CurrentBalance){
        this.id = Id;
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

    public BigDecimal getCurrentBalance(){
        return currentBalance;
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

    // Returns a string presentation of the product
    @Override
    public String toString(){
        return "Product[Id: " + id  + ", Type: " + type + ", Name: " + name +
        ", CurrentBalance: " + currentBalance + "]";
    }
}