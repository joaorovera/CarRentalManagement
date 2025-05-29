package com.carros.rentalcar.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carros.rentalcar.models.Car;
import com.carros.rentalcar.models.Customer;
import com.carros.rentalcar.models.Rental;
import com.carros.rentalcar.repositories.CarRepository;
import com.carros.rentalcar.repositories.CustomerRepository;
import com.carros.rentalcar.repositories.RentalRepository;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    public Rental findById(Long id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        return rental.orElseThrow(() -> new RuntimeException("Rental not found with id: " + id));
    }

    @Transactional
    public Rental create(Long customerId, List<Long> carIds, Rental rental) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + customerId));
        List<Car> cars = carRepository.findAllById(carIds);
        if (cars.size() != carIds.size()) {
            throw new RuntimeException("Um ou mais carros não foram encontrados");
        }

        rental.setCustomer(customer);
        rental.setCars(cars);
        return rentalRepository.save(rental);
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
