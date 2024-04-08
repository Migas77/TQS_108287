package com.tqs108287.app.hw1_bustickets.IT;

import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(locations = "classpath:application-it.properties")
public class TripControllerWithMockServiceIT {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Container
    public static MySQLContainer container = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
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

    @Autowired
    private TripRepository tripRepository;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.port = randomServerPort;
        // data inserted through data_test.sql -> no setup required
    }

    @Test
    void whenSearchFromOriginOnly_thenReturnStatus400() {
        given().
                param("originId", "1").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void whenSearchToDestOnly_thenReturnStatus400(){
        given().
                param("destinationId", "3").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void whenSearchFromOriginInvalid_thenReturnStatus404WithStopNotFoundMessage() {
        given().
                param("originId", "13213221").
                param("destinationId", "1").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body("message", is("Stop 13213221 Not Found"));
    }

    @Test
    void whenSearchFromDestinationInvalid_thenReturnStatus404WithStopNotFoundMessage() {
        given().
                param("originId", "1").
                param("destinationId", "13213225").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body("message", is("Stop 13213225 Not Found"));
    }
}
