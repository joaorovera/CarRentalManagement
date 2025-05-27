package com.carros.rentalcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carros.rentalcar.models.Customer;
import com.carros.rentalcar.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    @Transactional
    public Customer create(Customer customer) {
        customer.setId(null); // Ensure the ID is null for a new entity
        customer = this.customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Customer update(Customer customer) {
        Customer newCustomer = findById(customer.getId());
        newCustomer.setEmail(customer.getEmail());
        return customerRepository.save(newCustomer);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            this.customerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting customer with id: " + id, e);
        }
    }
}
