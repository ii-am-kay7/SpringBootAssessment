package com.enviro.assessment.grad001.kamielahheuvel.Services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enviro.assessment.grad001.kamielahheuvel.Models.*;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.InvestorRepository;

@Service
public class InvestorService {

    @Autowired
    private InvestorRepository investorRepository;

   // This method creates a new investor, validates details, and saves it in a transactional manner.
   @Transactional
   public Investor createNewInvestor(Investor newInvestor) throws AppExceptions {
       // Validate investor details before creating
       validateInvestorDetails(newInvestor);

       // Save the new investor
       return investorRepository.save(newInvestor);
   }

   // This method performs various validations on the details of an investor.
   private void validateInvestorDetails(Investor investor) throws AppExceptions {
       // Validate that name is not empty
       String investorName = investor.getName();
       if (investorName == null || investorName.trim().isEmpty()) {
           throw new AppExceptions("Investor name cannot be empty.");
       }

       // Validate that age is a number and not empty
       Integer investorAge = investor.getAge();
       if (investorAge == null || investorAge <= 0) {
           throw new AppExceptions("Investor age must be a positive number.");
       }

       // Validate that address and contact are not empty
       String investorAddress = investor.getAddress();
       String investorContact = investor.getContact();
       if (investorAddress == null || investorAddress.trim().isEmpty() ||
           investorContact == null || investorContact.trim().isEmpty()) {
           throw new AppExceptions("Investor address and contact cannot be empty.");
       }
   }

   // Retrieves an investor by name.
   public Investor getInvestorByName(String name) throws Exception{
       Investor investor = investorRepository.findByName(name);
       if (investor != null) {
           return investor;
       } else {
           throw new AppExceptions("Investor with name " + name + " not found");
       }
   }

   // Retrieves an investor by ID.
   public Investor getInvestorById(Long Id) throws Exception{
       Optional<Investor> OptionalInvestor = investorRepository.findById(Id);
       if (OptionalInvestor != null) {
           return OptionalInvestor.get();
       } else {
           throw new AppExceptions("Investor with id " + Id + " not found");
       }
   }

   // Retrieves an investor with associated products by ID.
   public Investor getInvestorWithProductsById(Long Id) throws Exception{
       Optional<Investor> optionalInvestor = investorRepository.findById(Id);
       if (optionalInvestor != null) {
           Investor investor = optionalInvestor.get();
           List<Product> productList = investor.getInvestments();
           return combineInvestorWithProducts(investor, productList); 
       } else {
           throw new AppExceptions("Investor with Id " + Id + " not found");
       }
   }

   // Retrieves products associated with an investor by ID.
   public List<Product> getInvestorProducts(Long Id) throws Exception{
       Optional<Investor> optionalInvestor = investorRepository.findById(Id);
       if (optionalInvestor != null) {
           Investor investor = optionalInvestor.get();
           List<Product> productList = investor.getInvestments();
           return productList; 
       } else {
           throw new AppExceptions("Investor with Id " + Id + " not found");
       }
   }

   // Checks if the age is valid for retirement.
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

   // Retrieves all investors.
   public List<Investor> getAllInvestors(){
       List<Investor> investors = investorRepository.findAll();
       if (investors != null) {
           return investors;
       }
       return new ArrayList<Investor>();
   }

   // Combines an investor with associated products.
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

    // Update an existing investor
    @Transactional
    public Investor updateInvestor(Long id, Investor updatedInvestor) throws AppExceptions {
        Optional<Investor> optionalExistingInvestor = investorRepository.findById(id);

        if (optionalExistingInvestor.isPresent()) {
            Investor existingInvestor = optionalExistingInvestor.get();

            // Update fields of the existing investor with the values from the updated investor
            existingInvestor.setName(updatedInvestor.getName());
            existingInvestor.setAge(updatedInvestor.getAge());
            existingInvestor.setAddress(updatedInvestor.getAddress());
            existingInvestor.setContact(updatedInvestor.getContact());

            // Save the updated investor
            return investorRepository.save(existingInvestor);
        } else {
            throw new AppExceptions("Investor with id " + id + " not found");
        }
    }

    // Delete an investor by ID
    @Transactional
    public void deleteInvestor(Long id) throws AppExceptions {
        Optional<Investor> optionalInvestor = investorRepository.findById(id);
        if (optionalInvestor.isPresent()) {
            investorRepository.deleteById(id);
        } else {
            throw new AppExceptions("Investor with id " + id + " not found");
        }
    }
}