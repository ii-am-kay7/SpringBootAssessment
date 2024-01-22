package com.enviro.assessment.grad001.kamielahheuvel.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.kamielahheuvel.Models.Product;
import com.enviro.assessment.grad001.kamielahheuvel.Respositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;

    public List<Product> getProductsByInvestor(Long investorId) throws Exception{
        List<Product> products = productRepository.findByInvestor(investorId);
        if (products != null) {
            return products;
        }else{
            throw new Exception("Products by" + investorId + " not found");
        }
    }

    public List<Product> getProductByName(String name) throws Exception{
        List<Product> products = productRepository.findByName(name);
        if (products != null) {
            return products;
        }else{
            throw new Exception("Products by" + name + " not found");
        }
    }

    public List<Product> getProductsByType(String Type) throws Exception{
        List<Product> products = productRepository.findByType(Type);
        if (products != null) {
            return products;
        }else{
            throw new Exception("Products by" + Type + " not found");
        }
    }

    public List<Product> getProductsWithBalanceBetween(int firstValue, int secValue) throws Exception{
        List<Product> products = productRepository.findByBalanceBetween(firstValue, secValue);
        if (products != null) {
            return products;
        }else{
            throw new Exception("Products by between" + firstValue + " and " + secValue + " not found");
        }
    }

    public List<Product> getProductsWithBalanceLessThanOrEqualTo(double maxPrice) throws Exception{
        List<Product> products = productRepository.findByBalanceLessThanEqual(maxPrice);
        if (products != null) {
            return products;
        }else{
            throw new Exception("Products with balance less than or equal to " + maxPrice + " not found");
        }
    }

    public List<Product> getProductsByInvestorAndType(Long investorId, String type) throws Exception{
        List<Product> products = productRepository.findByInvestorAndType(investorId, type);
        if (products != null) {
            return products;
        }else{
            throw new Exception("Products by InvestorId " + investorId + " and Type " + type + " not found");
        }
    }
}