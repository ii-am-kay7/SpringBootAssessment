package com.enviro.assessment.grad001.kamielahheuvel.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.InvestorRepository;

@Service
public class InvestorService {

    @Autowired
    private InvestorRepository investorRepository;

    public Investor getInvestorByName(String name) throws Exception{
        Investor investor = investorRepository.findByName(name);
        if (investor != null) {
            return investor;
        }else{
            throw new Exception("Investor with name " + name + " not found");
        }
        
    }

    public Investor getInvestorWithProductsByName(String name) throws Exception{
        Investor investor = investorRepository.findByName(name);
        if (investor != null) {
            List<Product> productList = investor.getInvestments();
            return combineInvestorWithProducts(investor, productList); 
        } else {
            throw new Exception("Investor with name " + name + " not found");
        }
    }

    private Investor combineInvestorWithProducts(Investor investor, List<Product> productList) {
        // Create a new Investor object or update the existing one with the associated products
        Investor resultInvestor = new Investor();
        resultInvestor.setId(investor.getId());
        resultInvestor.setName(investor.getName());
        resultInvestor.setAge(investor.getAge());
        resultInvestor.setAddress(investor.getAddress());
        resultInvestor.setContact(investor.getContact());
        resultInvestor.setInvestments(productList);
    
        return resultInvestor;
    }
    
}