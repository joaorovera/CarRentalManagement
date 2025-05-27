package com.carros.rentalcar.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carros.rentalcar.models.Rental;
import java.util.List;
import com.carros.rentalcar.models.Customer;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCustomer(Customer customer);
}
