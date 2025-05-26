package com.carros.rentalcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carros.rentalcar.models.Car;
import com.carros.rentalcar.repositories.CarRepository;

import jakarta.transaction.Transactional;

@Service
public class CarService {
    
    @Autowired
    private CarRepository carRepository;

    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    @Transactional
    public Car create(Car car) {
        car.setId(null); // Ensure the ID is null for a new entity
        car = this.carRepository.save(car);
        return car;
    }

    @Transactional
    public Car update(Car car) {
        Car newCar = findById(car.getId());
        newCar.setLicensePlate(car.getLicensePlate());
        return carRepository.save(newCar);
    }

    @Transactional
    public void delete(Long id) {
        findById(id);
        try {
            this.carRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting car with id: " + id, e);
        }
    }
}
