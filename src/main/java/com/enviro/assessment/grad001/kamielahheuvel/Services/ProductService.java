package com.enviro.assessment.grad001.kamielahheuvel.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.kamielahheuvel.Models.AppExceptions;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Investor;
import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;

    public Product getProductsById(Long productId) throws Exception{
        Optional<Product> OptionalProducts = productRepository.findById(productId);
        if (OptionalProducts.isPresent()) {
            return OptionalProducts.get();
        }else{
            throw new AppExceptions("Products by" + productId + " not found");
        }
    }

    public List<Product> getProductByName(String name) throws Exception{
        List<Product> products = productRepository.findByName(name);
        if (!products.isEmpty()) {
            return products;
        }else{
            return new ArrayList<Product>();        
        }
    }

    public List<Product> getProductsByType(String Type) throws Exception{
        List<Product> products = productRepository.findByType(Type);
        if (!products.isEmpty()) {
            return products;
        }else{
            return new ArrayList<Product>();        
        }
    }

    public List<Product> getProductsByCurrentBalance(BigDecimal balance) throws Exception{
        List<Product> products = productRepository.findByCurrentBalance(balance);
        if (!products.isEmpty()) {
            return products;
        }else{
            return new ArrayList<Product>();        
        }
    }

}