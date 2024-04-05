package com.tqs108287.app.lab3_2cars;

import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.data.CarRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.apache.http.HttpStatus;
import static io.restassured.RestAssured.given;
import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRestControllerIT_WithRestAssured {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withUsername("miguel")
            .withPassword("password")
            .withDatabaseName("test");



    // requires Spring Boot >= 2.2.6
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @LocalServerPort
    int randomServerPort;

    static final Logger log = LoggerFactory.getLogger(lookup().lookupClass());

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.port = randomServerPort;
    }

    @Test
    @Order(1)
    void whenPostCar_thenCreateCar() {
        Car porshe = new Car("porshe", "911 turbo s");

        Car returnedCar =
                given().
                        contentType(ContentType.JSON).
                        body(porshe).
                when().
                        post("/api/cars").
                then().
                        statusCode(HttpStatus.SC_CREATED).
                        body("maker", is("porshe")).
                        extract().
                        as(Car.class);

        assertEquals(porshe.getModel(), carRepository.findByCarId(returnedCar.getCarId()).getModel());
    }

    @Test
    @Order(2)
    void givenManyCars_whenGetCars_thenStatus200() throws Exception{
        given().
        when().
                get("/api/cars").
        then().
                statusCode(HttpStatus.SC_OK).
                body("size()", equalTo(7)).
                body("maker", containsInAnyOrder("porshe", "porshe", "opel", "volkswagen", "seat", "ford", "ford"));
    }

    @Test
    @Order(3)
    void givenManyCars_whenGetCarsByMaker_thenStatus200() throws Exception{
        given().
        when().
                get("/api/cars?maker=porshe").
        then().
                statusCode(HttpStatus.SC_OK).
                body("size()", equalTo(2)).
                body("maker", contains("porshe", "porshe"));
    }

    @Test
    @Order(4)
    void givenOneCarId_whenGetCar_thenStatus200() throws Exception{
        Car car0 = createTestCar("custom car", "top");

        given().
        when().
                get(String.format("/api/car/%d", car0.getCarId())).
        then().
                statusCode(HttpStatus.SC_OK).
                body("maker", is("custom car"));
    }

    private Car createTestCar(String maker, String model) {
        Car emp = new Car(maker, maker);
        return carRepository.saveAndFlush(emp);
    }
}
