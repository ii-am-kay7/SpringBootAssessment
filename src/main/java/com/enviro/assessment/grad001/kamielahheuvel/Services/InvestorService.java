package com.enviro.assessment.grad001.kamielahheuvel.Services;

import java.util.*;

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

    public Investor getInvestorById(Long Id) throws Exception{
        Optional<Investor> OptionalInvestor = investorRepository.findById(Id);
        if (OptionalInvestor != null) {
            return OptionalInvestor.get();
        }else{
            throw new Exception("Investor with id " + Id + " not found");
        }
        
    }

    public Investor getInvestorWithProductsById(Long Id) throws Exception{
        Optional<Investor> optionalInvestor = investorRepository.findById(Id);
        if (optionalInvestor != null) {
            Investor investor = optionalInvestor.get();
            List<Product> productList = investor.getInvestments();
            return combineInvestorWithProducts(investor, productList); 
        } else {
            throw new Exception("Investor with Id " + Id + " not found");
        }
    }

    public List<Product> getInvestorProducts(Long Id) throws Exception{
        Optional<Investor> optionalInvestor = investorRepository.findById(Id);
        if (optionalInvestor != null) {
            Investor investor = optionalInvestor.get();
            List<Product> productList = investor.getInvestments();
            return productList; 
        } else {
            throw new Exception("Investor with Id " + Id + " not found");
        }
    }

    public Boolean isValidAgeForRetirement(Long id){
        Optional<Investor> OptionalInvestor = investorRepository.findById(id);
        if (OptionalInvestor.isPresent()) {
            Investor investor = OptionalInvestor.get();
            Integer investorAge = investor.getAge();
            if (investorAge != null && investorAge < 65) {
                return true;
            } 
        }
        return false;
    }

    public List<Investor> getAllInvestors(){
        List<Investor> investors = investorRepository.findAll();
        if (investors != null) {
            return investors;
        }
        return new ArrayList<Investor>();
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