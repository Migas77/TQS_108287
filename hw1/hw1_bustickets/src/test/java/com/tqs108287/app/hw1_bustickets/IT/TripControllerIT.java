package com.tqs108287.app.hw1_bustickets.IT;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.List;
import static io.restassured.RestAssured.given;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(locations = "classpath:application-it.properties")
public class TripControllerIT {

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
    public void setUp(){
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

    @Test
    void whenSearchFromOriginToDestWithInvalidCurrency_thenReturnStatus404WithStopCurrencyFoundMessage() {
        given().
                param("originId", "1").
                param("destinationId", "3").
                param("departure_date", "2024-06-02").
                param("currency", "doesntexist").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body("message", is("Currency doesntexist Not Found"))
                .extract().statusLine();
    }

    @Test
    void whenSearchFromOriginToDestValidWithoutDateAndCurrency_thenReturnListTripDetails() {
        given().
                param("originId", "3").
                param("destinationId", "4").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_OK).
                body("size()", is(1)).
                body("id", contains(6)).
                body("currency", contains("EUR")).
                body("price", contains(20f)).
                body("departureTime", contains(LocalDate.now() + "T07:00:00"));
        // check if it's today's date
    }

    @Test
    void whenSearchFromOriginToDestValidWithDateAndUSD_thenReturnList() {
        List<TripDetailsDTO> tripDetailsDTOS =
            given().
                    param("originId", "1").
                    param("destinationId", "3").
                    param("departure_date", "2024-06-02").
                    param("currency", "USD").
            when().
                    get("api/trips").
            then().
                    statusCode(HttpStatus.SC_OK).
                    body("size()", is(2)).
                    body("id", Matchers.containsInAnyOrder(1, 2)).
                    body("currency", Matchers.containsInAnyOrder("USD", "USD")).
                    body("departureTime", containsInAnyOrder("2024-06-02T10:15:00", "2024-06-02T11:15:00")).
                    extract().body().jsonPath().getList(".", TripDetailsDTO.class);

        // 50*0.8f < price0 < 50*1.2f
        // 25*0.8f < price1 < 25*1.2f
        // The lowest values matches both assertions. The second assertion is contained in the first.
        // As I don't know which is which I'll just check the more restrictive one
        assertThat(tripDetailsDTOS).extracting(TripDetailsDTO::getPrice).anySatisfy(price -> {
            assertThat(price).isBetween(25*0.8f, 25*1.2f);
        });
    }

    @Test
    void whenGetTripWithValidId_thenReturnTripDetails() {
        given().
        when().
                get("api/trips/{id}", "1").
        then().
                statusCode(HttpStatus.SC_OK).
                body("id", is(1));
    }

    @Test
    void whenGetTripWithInvalidId_thenReturnStatus404() {
        given().
        when().
                get("api/trips/{id}", "113231221312").
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(is(Matchers.emptyOrNullString()));
    }
}
