package com.tqs108287.app.lab3_2cars.service;

import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.data.CarRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CarManagerService {
    private final CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId){
        // if null -> empty optional
        return Optional.ofNullable(carRepository.findByCarId(carId));
    }

}
