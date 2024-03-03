package com.tqs108287.app.lab3_2cars;

import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.data.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CarRestControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car porshe = new Car("porshe", "911 turbo s");

        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", porshe, Car.class);

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).extracting(Car::getMaker).containsOnly("porshe");
    }

    @Test
    void givenManyCars_whenGetCars_thenStatus200() throws Exception{
        createTestCar("porshe", "911 turbo s");
        createTestCar("opel", "corsa");
        createTestCar("volkswagen", "golf");

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("porshe","opel","volkswagen");
    }


    @Test
    void givenOneCarId_whenGetCar_thenStatus200() throws Exception{
        createTestCar(1L,"porshe", "911 turbo s");

        ResponseEntity<Car> entity = restTemplate.getForEntity("/api/car/1", Car.class);

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).extracting(Car::getMaker).containsOnly("porshe");
    }

    private void createTestCar(String maker, String model) {
        Car emp = new Car(maker, maker);
        carRepository.saveAndFlush(emp);
    }

    private void createTestCar(Long carId, String maker, String model) {
        Car emp = new Car(carId, maker, maker);
        carRepository.saveAndFlush(emp);
    }
}