package com.enviro.assessment.grad001.kamielahheuvel.Models;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
public class WithdrawalNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal withdrawalAmount;

    public WithdrawalNotice(){}

    public WithdrawalNotice(BigDecimal WithdrawalAmount){
        this.withdrawalAmount = WithdrawalAmount;
    }

    public Long getId(){
        return id;
    }

    public BigDecimal getWithdrawalAmount(){
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal WithdrawalAmount){
        this.withdrawalAmount = WithdrawalAmount;
    }

    // Returns a string presentation of the WithdrawalNotice
    @Override
    public String toString(){
        return "WithdrawalNotice[Id: " + id  + ", WithdrawalAmount: " + withdrawalAmount  + "]";
    }

}
