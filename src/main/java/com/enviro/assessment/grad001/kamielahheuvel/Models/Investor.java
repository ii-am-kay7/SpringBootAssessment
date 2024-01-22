package com.enviro.assessment.grad001.kamielahheuvel.Models;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Investor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String name;
    private Integer age;
    private String address; 
    private String contact;

    // Establishing a one-to-many relationship with Product
    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    private List<Product> investments;

    // Default constructor required by JPA
    public Investor(){}

    // Parameterized constructor for creating an Investor instance with data
    public Investor(Long Id, String Name, Integer Age, String Address, String Contact){
        this.id = Id;
        this.name = Name;
        this.age = Age;
        this.address = Address;
        this.contact = Contact;
    }

    // Getter and Setter methods 
    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Integer getAge(){
        return age;
    }

    public String getAddress(){
        return address;
    }

    public String getContact(){
        return contact;
    }

    public List<Product> getInvestments() {
        return investments;
    }

    public void setId(Long Id){
        this.id = Id;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public void setAge(Integer Age){
        this.age = Age;
    }

    public void setAddress(String Address){
        this.address = Address;
    }

    public void setContact(String Contact){
        this.contact = Contact;
    }

    public void setInvestments(List<Product> investments) {
        this.investments = investments;
    }

    // Returns a string presentation of the investor
    @Override
    public String toString(){
        return "Investor[Id: " + id + ", Name: " + name + ", Age: " + age + 
        ", Address: " + address + ", Contact: " + contact + ", investments: " + investments + "]";
    }
}
