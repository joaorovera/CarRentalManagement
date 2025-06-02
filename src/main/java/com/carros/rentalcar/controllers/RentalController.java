package com.carros.rentalcar.controllers;

import com.carros.rentalcar.models.Car;
import com.carros.rentalcar.models.Customer;
import com.carros.rentalcar.models.Rental;
import com.carros.rentalcar.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rental")
@Validated
@CrossOrigin(origins = "*")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/{id}")
    public ResponseEntity<Rental> findById(@PathVariable Long id) {
        Rental rental = rentalService.findById(id);
        return ResponseEntity.ok().body(rental);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Rental rental) {
        Customer customer = rental.getCustomer();
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("O campo customer.id é obrigatório");
        }
        Long customerId = customer.getId();

        List<Car> cars = rental.getCars();
        if (cars == null || cars.isEmpty()) {
            throw new IllegalArgumentException("A lista de cars não pode ser vazia");
        }
        List<Long> carIds = cars.stream()
                .map(Car::getId)
                .collect(Collectors.toList());
        Rental created = rentalService.create(customerId, carIds, rental);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Rental rental) {
        rental.setId(id);
        rentalService.update(rental);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rentalService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 
