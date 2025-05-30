package com.carros.rentalcar.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carros.rentalcar.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);
    
}
