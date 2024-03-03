package com.tqs108287.app.lab3_2cars.boundary;

import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.data.CarDTO;
import com.tqs108287.app.lab3_2cars.service.CarManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarRestController {

    private final CarManagerService carManagerService;

    public CarRestController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping("/cars" )
    public ResponseEntity<Car> createCar(@RequestBody CarDTO car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerService.save( car.toCarEntity() );
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        HttpStatus status = HttpStatus.OK;
        Car found = carManagerService.getCarDetails(id).orElse(null);
        return new ResponseEntity<>(found, status);
    }

}
