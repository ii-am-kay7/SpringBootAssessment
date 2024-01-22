package com.enviro.assessment.grad001.kamielahheuvel.Controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/investors")
public class InvestorController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, this is the InvestorController!";
    }
}