package com.carros.rentalcar.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.carros.rentalcar.models.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByModel(String model);
}
