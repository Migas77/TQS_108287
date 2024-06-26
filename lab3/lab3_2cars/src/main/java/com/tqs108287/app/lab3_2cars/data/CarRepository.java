package com.tqs108287.app.lab3_2cars.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public Car findByCarId(Long id);

    public List<Car> findByMaker(String maker);

    public List<Car> findAll();
}
