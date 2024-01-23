package com.enviro.assessment.grad001.kamielahheuvel.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class WithdrawalNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal withdrawalAmount;
    private LocalDateTime date;
    private String bankingDetails;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Default constructor required by JPA
    public WithdrawalNotice(){}

    // Parameterized constructor for creating an Investor instance with data
    public WithdrawalNotice(BigDecimal WithdrawalAmount, LocalDateTime date, String bankingDetails){
        this.withdrawalAmount = WithdrawalAmount;
        this.date = date;
        this.bankingDetails = bankingDetails;
    }

    // Getter and Setter methods
    public Long getId(){
        return id;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getBankingDetails(){
        return bankingDetails;
    }

    public BigDecimal getWithdrawalAmount(){
        return withdrawalAmount;
    }

    public Product getProduct(){
        return product;
    }

    public Investor getInvestor(){
        return product.getInvestor();
    }

    public BigDecimal getCurrentBalance(){
        return product.getCurrentBalance();
    }

    public void setId(Long Id){
        this.id = Id;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }

    public void setBankingDetails(String bankingDetails){
        this.bankingDetails = bankingDetails;
    }

    public void setWithdrawalAmount(BigDecimal WithdrawalAmount){
        this.withdrawalAmount = WithdrawalAmount;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    // Returns a string presentation of the WithdrawalNotice
    @Override
    public String toString(){
        return "WithdrawalNotice[Id: " + id  + ", WithdrawalAmount: " +
        withdrawalAmount  + ", Date: " + date + ", BankingDetails: " + bankingDetails + "]";
    }

}
