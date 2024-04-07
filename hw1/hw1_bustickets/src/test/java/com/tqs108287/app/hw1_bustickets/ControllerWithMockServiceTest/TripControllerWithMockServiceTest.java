package com.tqs108287.app.hw1_bustickets.ControllerWithMockServiceTest;

import com.tqs108287.app.hw1_bustickets.boundary.TripRestController;
import com.tqs108287.app.hw1_bustickets.entities.*;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@WebMvcTest(TripRestController.class)
public class TripControllerWithMockServiceTest {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITripService service;

    Trip trip_fromLisboa_toPorto;
    Trip trip_fromLisboa_toBraga;
    Trip trip_fromLisboa_toAveiro;
    Trip trip_fromCoimbra_toPorto;
    Trip trip_fromAveiro_toPorto;

    // TODO CHANGE BeforeEach later
    @BeforeEach
    void setup(){
        // Lisboa -> Coimbra -> Aveiro -> Porto
        // Lisboa -> Aveiro -> Braga
        // Lisboa -> Aveiro
        // Coimbra -> Porto
        // Aveiro -> Porto

        Stop stop_lisboa = new Stop(1L, "Lisboa", "Lisboa");
        Stop stop_coimbra = new Stop(2L, "Coimbra", "Coimbra");
        Stop stop_aveiro = new Stop(3L, "Aveiro", "Aveiro");
        Stop stop_porto = new Stop(4L, "Porto", "Porto");
        Stop stop_braga = new Stop(5L, "Braga", "Braga");
        Leg leg_fromLisboa_toCoimbra = new Leg(1L, stop_lisboa, stop_coimbra, LocalTime.of(2, 2));
        Leg leg_fromLisboa_toAveiro = new Leg(2L, stop_lisboa, stop_aveiro, LocalTime.of(2, 30));
        Leg leg_fromCoimbra_toAveiro = new Leg(3L, stop_coimbra, stop_aveiro, LocalTime.of(0, 48));
        Leg leg_fromCoimbra_toPorto = new Leg(4L, stop_coimbra, stop_porto, LocalTime.of(1, 15));
        Leg leg_fromAveiro_toPorto = new Leg(5L, stop_aveiro, stop_porto, LocalTime.of(0, 48));
        Leg leg_fromAveiro_toBraga = new Leg(6L, stop_aveiro, stop_braga, LocalTime.of(1, 19));
        Route route_fromLisboa_toPorto = new Route(1L, List.of(leg_fromLisboa_toCoimbra, leg_fromCoimbra_toAveiro, leg_fromAveiro_toPorto));
        Route route_fromLisboa_toBraga = new Route(2L, List.of(leg_fromLisboa_toAveiro, leg_fromAveiro_toBraga));
        Route route_fromLisboa_toAveiro = new Route(3L, List.of(leg_fromLisboa_toAveiro));
        Route route_fromCoimbra_toPorto = new Route(4L, List.of(leg_fromCoimbra_toPorto));
        Route route_fromAveiro_toPorto = new Route(5L, List.of(leg_fromAveiro_toPorto));
        trip_fromLisboa_toPorto = new Trip(1L, route_fromLisboa_toPorto, 50, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        trip_fromLisboa_toBraga = new Trip(2L, route_fromLisboa_toBraga, 47, 35f, LocalDateTime.of(2024, 6, 2, 11, 15));
        trip_fromLisboa_toAveiro = new Trip(3L, route_fromLisboa_toAveiro, 50, 20f, LocalDateTime.of(2024, 6, 3, 10, 15));
        trip_fromCoimbra_toPorto = new Trip(4L, route_fromCoimbra_toPorto, 30, 22.5f, LocalDateTime.of(2024, 6, 2, 11, 30));
        trip_fromAveiro_toPorto = new Trip(5L, route_fromAveiro_toPorto, 50, 20f, LocalDateTime.of(2024, 6, 4, 6, 15));
    }

    @Test
    void givenManyTrips_WhenSearchFromOriginOnly_thenReturnStatus400() {
        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("originId", "1").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_BAD_REQUEST).
                        body(is(Matchers.emptyOrNullString()));

        verify(service, times(0)).getAllTripsOnDate(anyLong(), anyLong(), any(LocalDate.class));
    }

    @Test
    void givenManyTrips_WhenSearchToDestOnly_thenReturnStatus400() {
        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("destinationId", "3").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_BAD_REQUEST).
                        body(is(Matchers.emptyOrNullString()));

        verify(service, times(0)).getAllTripsOnDate(anyLong(), anyLong(), any(LocalDate.class));
    }

    @Test
    void givenManyTrips_whenSearchFromOriginToDest_thenReturnList() {
        when(service.getAllTripsOnDate(anyLong(), anyLong(), any(LocalDate.class)))
                .thenReturn(List.of(trip_fromLisboa_toPorto, trip_fromAveiro_toPorto, trip_fromLisboa_toAveiro));

        RestAssuredMockMvc.
        given().
                mockMvc(mockMvc).
                param("originId", "1").
                param("destinationId", "3").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_OK).
                body("size()", is(3)).
                body("id", Matchers.containsInAnyOrder(trip_fromLisboa_toPorto.getId().intValue(), trip_fromAveiro_toPorto.getId().intValue(), trip_fromLisboa_toAveiro.getId().intValue()));

        verify(service, times(1)).getAllTripsOnDate(anyLong(), anyLong(), any(LocalDate.class));
    }

    @Test
    void givenManyTrips_whenSearchFromOriginToDestWithDate_thenReturnList() {
        when(service.getAllTripsOnDate(anyLong(), anyLong(), any(LocalDate.class)))
                .thenReturn(List.of(trip_fromLisboa_toPorto, trip_fromLisboa_toBraga, trip_fromCoimbra_toPorto));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("originId", "1").
                        param("destinationId", "3").
                        param("departureDate", "2024-06-02").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("size()", is(3)).
                        body("id", Matchers.containsInAnyOrder(trip_fromLisboa_toPorto.getId().intValue(), trip_fromLisboa_toBraga.getId().intValue(), trip_fromCoimbra_toPorto.getId().intValue()));

        verify(service, times(1)).getAllTripsOnDate(anyLong(), anyLong(), any(LocalDate.class));
    }

    @Test
    void whenGetTripWithValidId_thenReturnTripDetails() {
        when(service.getTripById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/trips/{id}", "1").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("id", is(1));

        verify(service, times(1)).getTripById(anyLong());
    }

    @Test
    void whenGetTripWithInValidId_thenReturnStatus404() {
        when(service.getTripById(anyLong())).thenReturn(Optional.empty());

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/trips/{id}", "1").
                then().
                        statusCode(HttpStatus.SC_NOT_FOUND).
                        body(is(Matchers.emptyOrNullString()));

        verify(service, times(1)).getTripById(anyLong());
    }
}
