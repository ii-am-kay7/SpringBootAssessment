package com.enviro.assessment.grad001.kamielahheuvel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Models.WithdrawalNotice;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.*;

@SpringBootApplication
public class MainClass {

	public static void main(String[] args) {
		SpringApplication.run(MainClass.class, args);
	}

    // CommandLineRunner bean to seed demo data when the application starts
    @Bean
    public CommandLineRunner seedDemoData(InvestorRepository investorRepository, ProductRepository productRepository, WithdrawalNoticeRepository withdrawalNoticeRepository) {
        return args -> {
            // Seed investors and products
            seedInvestors(investorRepository);
            seedProducts(productRepository, investorRepository);
            seedWithdrawalNotices(withdrawalNoticeRepository, productRepository);
        };
    }

    // Method to seed investors with demo data
    private void seedInvestors(InvestorRepository investorRepository) {
        try {
            // Create instances of investors
            Investor investor1 = new Investor("John Doe", 66, "123 Main St", "john.doe@example.com");
            Investor investor2 = new Investor("Jane Smith", 25, "456 Oak St", "jane.smith@example.com");

            // Save investors to the database 
            investorRepository.saveAll(List.of(investor1, investor2));
        } catch (Exception e) {
            // Handle exceptions related to saving investors
            System.err.println("Error seeding investors: " + e.getMessage());
        }
    }

     // Method to seed products linked to investors with demo data
     private void seedProducts(ProductRepository productRepository, InvestorRepository investorRepository) {
        try {
            // Retrieve investors from the database
            Investor investor1 = investorRepository.findByName("John Doe");
            Investor investor2 = investorRepository.findByName("Jane Smith");

            if (investor1 != null && investor2 != null) {
                // Link products to investor1 and investor2
                Product product1 = new Product("RETIREMENT", "Fund A", BigDecimal.valueOf(10000));
                product1.setInvestor(investor1);

                Product product2 = new Product("SAVINGS", "Account B", BigDecimal.valueOf(5000));
                product2.setInvestor(investor2);

                // Save products to the database
                productRepository.saveAll(List.of(product1, product2));
            } else {
                // Handle the case where investors are not found
                throw new AppExceptions("Investors not found");
            }
        } catch (Exception e) {
            // Handle exceptions related to saving products or finding investors
            System.err.println("Error seeding products: " + e.getMessage());
        }
    }
    
    // Method to seed withdrawal notices linked to products with demo data
    private void seedWithdrawalNotices(
        WithdrawalNoticeRepository withdrawalNoticeRepository,
        ProductRepository productRepository
    ) {
    try {
        // Retrieve products from the database
        Product product1 = productRepository.findByTypeAndName("RETIREMENT", "Fund A");
        Product product2 = productRepository.findByTypeAndName("SAVINGS", "Account B");

        if (product1 != null && product2 != null) {
            // Link withdrawal notices to product1 and product2
            WithdrawalNotice notice1 = new WithdrawalNotice(BigDecimal.valueOf(500), LocalDateTime.now(), "BankingDetails1");
            notice1.setProduct(product1);

            WithdrawalNotice notice2 = new WithdrawalNotice(BigDecimal.valueOf(1000), LocalDateTime.now(), "BankingDetails2");
            notice2.setProduct(product2);

            // Save withdrawal notices to the database
            withdrawalNoticeRepository.saveAll(List.of(notice1, notice2));
        } else {
            // Handle the case where products are not found
            throw new AppExceptions("Products not found");
        }
    } catch (Exception e) {
        // Handle exceptions related to saving withdrawal notices or finding products
        System.err.println("Error seeding withdrawal notices: " + e.getMessage());
    }
}


}
