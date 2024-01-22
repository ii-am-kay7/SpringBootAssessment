package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Services.InvestorService;

@RestController
@RequestMapping("/investors")
public class InvestorController {

    @Autowired
    private InvestorService investorService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is the InvestorController!";
    }

    @GetMapping("name/{investorName}")
    public ResponseEntity<Investor> getInvestorByName(@PathVariable String investorName) throws Exception {
        Investor investor = investorService.getInvestorByName(investorName);
        if (investor != null) {
            return ResponseEntity.ok(investor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("id/{investorId}")
    public ResponseEntity<Investor> getInvestorById(@PathVariable Long investorId) throws Exception {
        Investor investor = investorService.getInvestorById(investorId);
        if (investor != null) {
            return ResponseEntity.ok(investor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{investorId}/products")
    public ResponseEntity<List<Product>> getInvestorProducts(@PathVariable Long investorId) throws Exception {
        List<Product> products = investorService.getInvestorProducts(investorId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{investorId}/Investor&products")
    public ResponseEntity<Investor> getInvestorWithProducts(@PathVariable Long investorId) throws Exception {
        Investor Investor = investorService.getInvestorWithProductsById(investorId);
        return ResponseEntity.ok(Investor);
    }
}