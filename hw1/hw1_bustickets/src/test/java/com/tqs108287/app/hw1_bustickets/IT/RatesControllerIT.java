package com.tqs108287.app.hw1_bustickets.IT;

import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(locations = "classpath:application-it.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RatesControllerIT {

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

    @BeforeEach
    public void setUp() throws Exception {
        RestAssured.port = randomServerPort;
        // data inserted through data_test.sql -> no setup required
    }

    @Test
    @Order(1)
    void givenManyNoSearches_whenGetMetrics_thenReturnMetricsWith0HitsAndMisses() {
        given().
        when().
                get("api/cache").
        then().
                statusCode(HttpStatus.SC_OK).
                body("cacheHits", is(0)).
                body("cacheMisses", is(0));
    }

    @Test
    @Order(2)
    void givenManyTripSearches_whenGetMetrics_thenReturnMetrics() {
        given().param("originId", "1").param("destinationId", "3").param("departure_date", "2024-06-02").
                param("currency", "USD").when().get("api/trips");
        given().param("originId", "1").param("destinationId", "3").param("departure_date", "2024-06-02").
                param("currency", "USD").when().get("api/trips");
        given().param("originId", "1").param("destinationId", "3").param("departure_date", "2024-06-02").
                param("currency", "USD").when().get("api/trips");
        given().param("originId", "1").param("destinationId", "3").param("departure_date", "2024-06-02").
                param("currency", "GBP").when().get("api/trips");
        given().param("originId", "1").param("destinationId", "3").param("departure_date", "2024-06-02").
                param("currency", "GBP").when().get("api/trips");

        given().
        when().
                get("api/cache").
        then().
                statusCode(HttpStatus.SC_OK).
                body("cacheHits", is(3)).
                body("cacheMisses", is(2));
    }
}
