package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Services.InvestorService;

@RestController
@RequestMapping("/investors")
public class InvestorController {

    @Autowired
    private InvestorService investorService;

    // Hello endpoint for testing
    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is the InvestorController!";
    }

    // Endpoint to show the new investor 
    @GetMapping("/new_investor")
    public ModelAndView showNewInvestorForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new_investor");
        return modelAndView;
    }

    // Endpoint to create a new investor
    @PostMapping("/new_investor.action")
    public ResponseEntity<Investor> createNewInvestor(
            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String address,
            @RequestParam String contact) {

        try {
            // Create a new instance of Investor using the provided request parameters
            Investor newInvestor = new Investor(name, age, address, contact);

            // Call the service method to create the new investor
            Investor createdInvestor = investorService.createNewInvestor(newInvestor);

            // Return the created investor in the response
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInvestor);
        } catch (AppExceptions e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint to get an investor by name
    @GetMapping("name/{investorName}")
    public ModelAndView getInvestorByName(@PathVariable String investorName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("investor");

        try {
            Investor investor = investorService.getInvestorByName(investorName);
            modelAndView.addObject("investor", investor); // Add the Investor object to the model
        } catch (Exception e) {
            modelAndView.setViewName("error"); // Set the view name for error handling
            modelAndView.addObject("errorMessage", "Investor not found"); // Add error message to the model
        }

        return modelAndView;
    }

    // Endpoint to handle the form submission when an existing investor enters their name
    @PostMapping("/name")
    public ModelAndView submitInvestorName(@RequestParam String investorName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("investor");

        try {
            Investor investor = investorService.getInvestorByName(investorName);
            modelAndView.addObject("investor", investor);
        } catch (UnsupportedEncodingException e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("errorMessage", "Error decoding investorName");
        } catch (Exception e) {
            modelAndView.setViewName("error");
            modelAndView.addObject("errorMessage", "Investor not found");
        }

        return modelAndView;
    }

    // Endpoint to get an investor by ID
    @GetMapping("id/{investorId}")
    public ResponseEntity<Investor> getInvestorById(@PathVariable Long investorId) throws Exception {
        Investor investor = investorService.getInvestorById(investorId);
        return ResponseEntity.ok(investor);
    }

    // Endpoint to get products associated with an investor by ID
    @GetMapping("/{investorId}/products")
    public ResponseEntity<List<Product>> getInvestorProducts(@PathVariable Long investorId) throws Exception {
        List<Product> products = investorService.getInvestorProducts(investorId);
        return ResponseEntity.ok(products);
    }

    // Endpoint to get an investor with associated products by ID
    @GetMapping("/{investorId}/Investor&products")
    public ResponseEntity<Investor> getInvestorWithProducts(@PathVariable Long investorId) throws Exception {
        Investor investor = investorService.getInvestorWithProductsById(investorId);
        return ResponseEntity.ok(investor);
    }

    // Endpoint to get all investors
    @GetMapping("/all")
    public ResponseEntity<List<Investor>> getAllInvestors() throws Exception {
        List<Investor> investor = investorService.getAllInvestors();
        return ResponseEntity.ok(investor);
    }

    // Endpoint to update an existing investor by ID
    @PutMapping("/{investorId}")
    public ResponseEntity<Investor> updateInvestor(@PathVariable Long investorId, @RequestBody Investor updatedInvestor) throws Exception {
        Investor investor = investorService.updateInvestor(investorId, updatedInvestor);
        return ResponseEntity.ok(investor);
    }

    // Endpoint to delete an investor by ID
    @DeleteMapping("/{investorId}")
    public ResponseEntity<String> deleteInvestor(@PathVariable Long investorId) throws Exception {
        investorService.deleteInvestor(investorId);
        return ResponseEntity.ok("Investor with ID " + investorId + " deleted successfully");
    }
}
