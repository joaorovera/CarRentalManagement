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

    @Transactional
    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Transactional
    public Car update(Car car) {
        return carRepository.save(car);
    }

    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
