package com.enviro.assessment.grad001.kamielahheuvel;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
// import com.enviro.assessment.grad001.kamielahheuvel.Models.WithdrawalNotice;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.*;

@SpringBootApplication
public class MainClass {

	public static void main(String[] args) {
		SpringApplication.run(MainClass.class, args);
	}

	 @Bean
    public CommandLineRunner seedDemoData(InvestorRepository investorRepository, ProductRepository productRepository) {
        return args -> {
            seedInvestors(investorRepository);
            seedProducts(productRepository, investorRepository);
            // seedWithdrawalNotices(withdrawalNoticeRepository);
        };
    }

    private void seedInvestors(InvestorRepository investorRepository) {
        Investor investor1 = new Investor((long) 1, "John Doe", 66, "123 Main St", "john.doe@example.com");
        Investor investor2 = new Investor((long) 2, "Jane Smith", 25, "456 Oak St", "jane.smith@example.com");

        investorRepository.saveAll(List.of(investor1, investor2));
    }

    private void seedProducts(ProductRepository productRepository, InvestorRepository investorRepository) {
        Investor investor = new Investor();
        // Set other properties for the investor if needed
        investorRepository.save(investor);
    
        Product product1 = new Product((long) 1, "RETIREMENT", "Fund A", BigDecimal.valueOf(10000));        product1.setInvestor(investor);
        Product product2 = new Product((long) 2, "SAVINGS", "Account B", BigDecimal.valueOf(5000));        product2.setInvestor(investor);
        productRepository.saveAll(List.of(product1, product2));
    }

    // private void seedWithdrawalNotices(WithdrawalNoticeRepository withdrawalNoticeRepository) {
    //     // Add logic to seed withdrawal notices
    //     // Example:
    //     WithdrawalNotice notice1 = new WithdrawalNotice(productRepository.findById(1L).orElse(null), BigDecimal.valueOf(500));
    //     WithdrawalNotice notice2 = new WithdrawalNotice(productRepository.findById(2L).orElse(null), BigDecimal.valueOf(1000));

    //     withdrawalNoticeRepository.saveAll(List.of(notice1, notice2));
    // }

}
