package com.example.gym.repositories;

import com.example.gym.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT name FROM customers WHERE customers.gender='F'", nativeQuery = true)
     List<String> getCustomersByGender();


}
