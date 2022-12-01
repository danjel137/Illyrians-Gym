package com.example.gym.service;

import com.example.gym.exception.CustomerNotFoundException;
import com.example.gym.model.Customer;
import com.example.gym.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerService {
    private  final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerRepository.saveAndFlush(customer), HttpStatus.CREATED);
    }

    public ResponseEntity<Customer> getCustomerById(Long customer_id) {
        Customer customer = customerRepository.findById(customer_id).orElseThrow(() -> new CustomerNotFoundException("Customer not exist with id :" + customer_id));
        return ResponseEntity.ok(customer);
    }

    public ResponseEntity<Customer> updateCustomer(Long customer_id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(customer_id).orElseThrow(() -> new CustomerNotFoundException("Customer not exist with id :" + customer_id));
        existingCustomer.setName(customer.getName());
        existingCustomer.setSurname(customer.getSurname());
        existingCustomer.setBirthday(customer.getBirthday());
        existingCustomer.setGender(customer.getGender());
        existingCustomer.setPhone_number(customer.getPhone_number());
        existingCustomer.setStatus(customer.isStatus());
        Customer updatedCustomer = customerRepository.saveAndFlush(customer);
        return ResponseEntity.ok(updatedCustomer);

    }

    public ResponseEntity<Long> deleteCustomer(@PathVariable Long customer_id) {
        Customer customer = customerRepository.findById(customer_id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not exist with id :" + customer_id));
        customerRepository.delete(customer);

        return new ResponseEntity<>(customer_id, HttpStatus.OK);
    }

    public List<String> getCustomerByGender() {
        List<String> customers = customerRepository.getCustomersByGender();
        return customers;
    }
}