package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Endpoint to show the investment check form
    @GetMapping("/check")
    public String showInvestmentCheckForm() {
        return "investment_check";
    }

    // Endpoint to process the investment check form
    @PostMapping("/check")
    public String processInvestmentCheck(@RequestParam String investedBefore,
                                         @RequestParam(required = false) String investorName,
                                         Model model) throws Exception {
        System.out.println("investedBefore: " + investedBefore);
        System.out.println("investorName: " + investorName);
        if ("Yes".equalsIgnoreCase(investedBefore)) {
            // User has invested before, try to find the investor
            Investor existingInvestor = investorService.getInvestorByName(investorName);
            if (existingInvestor != null) {
                // Investor found, you can display details or redirect to another page
                model.addAttribute("investor", existingInvestor);
                return "investor_details";
            } else {
                // Investor not found, handle as needed (e.g., show an error message)
                model.addAttribute("error", "Investor not found");
                return "error_page";
            }
        } else if ("No".equalsIgnoreCase(investedBefore)) {
            // User has not invested before, you can handle creating a new investor
            return "new_investor_form"; // Redirect to a new investor form
        } else {
            // Handle other cases or show an error message
            model.addAttribute("error", "Invalid input");
            return "error_page";
        }
    }

    // Endpoint to get an investor by name
    @GetMapping("name/{investorName}")
    public ResponseEntity<Investor> getInvestorByName(@PathVariable String investorName) throws Exception {
        Investor investor = investorService.getInvestorByName(investorName);
        return ResponseEntity.ok(investor);
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
