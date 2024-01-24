package com.enviro.assessment.grad001.kamielahheuvel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.enviro.assessment.grad001.kamielahheuvel.Services.SeedDataService;


@SpringBootApplication
public class MainClass {

    @Autowired
    private SeedDataService seedDataService;

    // CommandLineRunner bean to seed demo data when the application starts
    @Bean
    public CommandLineRunner seedDemoData() {
        return args -> {
            // Seed investors, products, and withdrawal notices
            seedDataService.seedData();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MainClass.class, args);
    }
}
