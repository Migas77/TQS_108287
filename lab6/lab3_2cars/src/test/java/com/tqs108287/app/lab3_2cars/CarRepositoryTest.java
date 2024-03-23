package com.tqs108287.app.lab3_2cars;

import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.data.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarByExistingId_thenReturnCar(){
        Car porshe = new Car("porshe", "911 turbo s");
        entityManager.persistAndFlush(porshe);

        Car found = carRepository.findByCarId(porshe.getCarId());
        assertThat(found).isEqualTo(porshe);
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-111L); // provided Optional
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car porshe = new Car("porshe", "911 turbo s");
        Car opel = new Car("opel", "corsa");
        Car volkswagen = new Car("volkswagen", "golf");

        entityManager.persist(porshe);
        entityManager.persist(opel);
        entityManager.persist(volkswagen);
        entityManager.flush();
        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(porshe.getMaker(), opel.getMaker(), volkswagen.getMaker());
    }

    @Test
    void givenSetOfCars_whenFindByMaker_thenReturnMakerCars() {
        Car porshe0 = new Car("porshe", "911 turbo s");
        Car porshe1 = new Car("porshe", "gt3rs");
        Car opel = new Car("opel", "corsa");
        Car volkswagen = new Car("volkswagen", "golf");

        entityManager.persist(porshe0);
        entityManager.persist(porshe1);
        entityManager.persist(opel);
        entityManager.persist(volkswagen);
        entityManager.flush();
        List<Car> porshes = carRepository.findByMaker("porshe");

        assertThat(porshes).hasSize(2).extracting(Car::getMaker).containsOnly("porshe", "porshe");
    }
}
