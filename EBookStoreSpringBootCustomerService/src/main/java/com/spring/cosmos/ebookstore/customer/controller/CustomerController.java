package com.spring.cosmos.ebookstore.customer.controller;

import com.azure.cosmos.models.PartitionKey;
import com.spring.cosmos.ebookstore.customer.model.Customer;
import com.spring.cosmos.ebookstore.customer.model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @GetMapping(value = "/ebooks/customers/{customerId}")
    public Customer getCustomer(@PathVariable String customerId){
       return customerRepository.findById(customerId, new PartitionKey(customerId)).get();
    }

    @PostMapping(value ="/ebooks/customers")
    public Customer createCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }
}

