package com.tqs108287.app.hw1_bustickets.IT;

import com.tqs108287.app.hw1_bustickets.dto.ReservationDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

import static io.restassured.RestAssured.given;
import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.Matchers.*;
import static org.slf4j.LoggerFactory.getLogger;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(locations = "classpath:application-it.properties")
public class ReservationControllerIT {

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
    void givenInvalidTrip_whenMakeReservation_thenReturnStatus404() {
        given().
                contentType(ContentType.JSON).
                body(new ReservationDTO(1132312L, 1, "Cliente", "Morada")).
        when().
                post("api/reservations").
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(is(Matchers.emptyOrNullString()));
    }

    @Test
    void givenValidTrip_whenMakeReservationOnInvalidSeat_thenReturnStatus409() {
        given().
                        contentType(ContentType.JSON).
                        body(new ReservationDTO(1L, 1312321, "Cliente", "Morada")).
                when().
                        post("api/reservations").
                then().
                        statusCode(HttpStatus.SC_CONFLICT).
                        body(is(Matchers.emptyOrNullString()));
    }

    @Test
    void givenValidFullTrip_whenMakeReservation_thenReturnStatus409() {
        given().
                contentType(ContentType.JSON).
                body(new ReservationDTO(2L, 1, "Cliente", "Morada")).
        when().
                post("api/reservations").
        then().
                statusCode(HttpStatus.SC_CONFLICT).
                body(is(Matchers.emptyOrNullString()));
    }

    @Test
    void givenValidNotFullTrip_whenMakeReservationOnOccupiedSeat_thenReturnStatus409() {
        given().
                contentType(ContentType.JSON).
                body(new ReservationDTO(1L, 1, "Cliente", "Morada")).
        when().
                post("api/reservations").
        then().
                statusCode(HttpStatus.SC_CONFLICT).
                body(is(Matchers.emptyOrNullString()));
    }

    @Test
    void givenValidNotFullTrip_whenMakeReservation_thenReturnReservation() {
        given().
                contentType(ContentType.JSON).
                body(new ReservationDTO(3L, 2, "Cliente", "Morada")).
        when().
                post("api/reservations").
        then().
                statusCode(HttpStatus.SC_OK).
                body("trip.id", is(3));

        // verify available seat numbers on reserved trip
        given().
        when().
                get("api/trips/3").
        then().
                statusCode(HttpStatus.SC_OK).
                body("id", is(3)).
                body("availableSeats", not(contains(2))).
                body("availableSeats", contains(1,3,4));
    }

    @Test
    void givenValidUUID_whenSearchReservation_thenReturnReservation() {
        given().
        when().
                get("api/reservations/aa0f7252-309c-11ea-a72a-0242ac130002").
        then().
                statusCode(HttpStatus.SC_OK).
                body("trip.id", is(1));
    }

    @Test
    void givenInvalidUUID_whenSearchReservation_thenReturnStatus404() {
        given().
        when().
                get("api/reservations/aaaaaaaa-aaac-11ea-a72a-0242ac130002").
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(is(Matchers.emptyOrNullString()));
    }

}
