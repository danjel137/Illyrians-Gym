package com.example.gym.controller;

import com.example.gym.model.Customer;
import com.example.gym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CustomerController {
    private  final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/customers/{customer_id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customer_id) {
        return customerService.getCustomerById(customer_id);
    }

    @PutMapping("/customers/{customer_id}")
    public ResponseEntity<Customer> updateGym(@PathVariable Long customer_id, @RequestBody Customer customer) {
        return customerService.updateCustomer(customer_id, customer);
    }

    @DeleteMapping("/customers/{customer_id}")
    public ResponseEntity<Long>deleteCustomer(@PathVariable Long customer_id) {
        return customerService.deleteCustomer(customer_id);
    }

    @GetMapping("/customers/gender")
    public List<String> getCustomersByGender() {
        return customerService.getCustomerByGender();

    }

}
