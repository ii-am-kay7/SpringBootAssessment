package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.enviro.assessment.grad001.kamielahheuvel.Services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is the ProductController!";
    }

    
}