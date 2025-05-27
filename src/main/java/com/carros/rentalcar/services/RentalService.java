package com.carros.rentalcar.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carros.rentalcar.models.Customer;
import com.carros.rentalcar.models.Rental;
import com.carros.rentalcar.repositories.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerService customerService;

    public Rental findById(Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.orElseThrow(() -> new RuntimeException("Rental not found with id: " + id));
    }

    @Transactional
    public Rental create(Rental rental) {
        Customer customer = customerService.findById(rental.getCustomer().getId());
        rental.setId(null);
        rental.setCustomer(customer);
        rental = this.rentalRepository.save(rental);
        return rental;
    }

    @Transactional
    public Rental update(Rental rental) {
        Rental newRental = findById(rental.getId());
        newRental.setReturnDate(rental.getReturnDate());
        return rentalRepository.save(newRental);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            this.rentalRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting rental with id: " + id, e);
        }
    }
}
