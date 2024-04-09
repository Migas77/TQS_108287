package com.tqs108287.app.hw1_bustickets.ControllerWithMockServiceTest;

import com.tqs108287.app.hw1_bustickets.boundary.TripRestController;
import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.*;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
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
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@WebMvcTest(TripRestController.class)
public class TripControllerWithMockServiceTest {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITripService tripService;

    @MockBean
    private IStopService stopService;

    Trip trip_fromLisboa_toPorto;
    Trip trip_fromLisboa_toBraga;
    Trip trip_fromLisboa_toAveiro;
    Trip trip_fromCoimbra_toPorto;
    Trip trip_fromAveiro_toPorto;
    Trip trip_fromAveiro_toPorto_today;
    TripDetailsDTO tripDetails_fromLisboa_toPorto;
    TripDetailsDTO tripDetails_fromLisboa_toBraga;
    TripDetailsDTO tripDetails_fromLisboa_toAveiro;
    TripDetailsDTO tripDetails_fromCoimbra_toPorto;
    TripDetailsDTO tripDetails_fromAveiro_toPorto;
    TripDetailsDTO tripDetails_fromAveiro_toPorto_today;

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
        trip_fromLisboa_toPorto = new Trip(1L, route_fromLisboa_toPorto, Set.of(), 50, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        trip_fromLisboa_toBraga = new Trip(2L, route_fromLisboa_toBraga, Set.of(), 47, 35f, LocalDateTime.of(2024, 6, 2, 11, 15));
        trip_fromLisboa_toAveiro = new Trip(3L, route_fromLisboa_toAveiro, Set.of(), 50, 20f, LocalDateTime.of(2024, 6, 3, 10, 15));
        trip_fromCoimbra_toPorto = new Trip(4L, route_fromCoimbra_toPorto, Set.of(), 30, 22.5f, LocalDateTime.of(2024, 6, 2, 11, 30));
        trip_fromAveiro_toPorto = new Trip(5L, route_fromAveiro_toPorto, Set.of(), 50, 20f, LocalDateTime.of(2024, 6, 4, 6, 15));
        trip_fromAveiro_toPorto_today = new Trip(6L, route_fromAveiro_toPorto, Set.of(), 50, 20f, LocalDateTime.now());
        tripDetails_fromLisboa_toPorto = TripDetailsDTO.fromTripEntity(trip_fromLisboa_toPorto);
        tripDetails_fromLisboa_toPorto.setCurrency("USD");
        tripDetails_fromLisboa_toPorto.setPrice(tripDetails_fromLisboa_toPorto.getPrice()*1.1f);
        tripDetails_fromLisboa_toBraga = TripDetailsDTO.fromTripEntity(trip_fromLisboa_toBraga);
        tripDetails_fromLisboa_toBraga.setCurrency("USD");
        tripDetails_fromLisboa_toBraga.setPrice(tripDetails_fromLisboa_toBraga.getPrice()*1.1f);
        tripDetails_fromLisboa_toAveiro = TripDetailsDTO.fromTripEntity(trip_fromLisboa_toAveiro);
        tripDetails_fromCoimbra_toPorto = TripDetailsDTO.fromTripEntity(trip_fromCoimbra_toPorto);
        tripDetails_fromCoimbra_toPorto.setCurrency("USD");
        tripDetails_fromCoimbra_toPorto.setPrice(tripDetails_fromCoimbra_toPorto.getPrice()*1.1f);
        tripDetails_fromAveiro_toPorto = TripDetailsDTO.fromTripEntity(trip_fromAveiro_toPorto);
        tripDetails_fromAveiro_toPorto_today = TripDetailsDTO.fromTripEntity(trip_fromAveiro_toPorto_today);
    }

    @Test
    void whenSearchFromOriginOnly_thenReturnStatus400() {
        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("originId", "1").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_BAD_REQUEST).
                        body(is(Matchers.emptyOrNullString()));

        verify(stopService, times(0)).getStopById(anyLong());
        verify(tripService, times(0)).getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class));
    }

    @Test
    void whenSearchToDestOnly_thenReturnStatus400() {
        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("destinationId", "3").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_BAD_REQUEST).
                        body(is(Matchers.emptyOrNullString()));

        verify(stopService, times(0)).getStopById(anyLong());
        verify(tripService, times(0)).getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class));
    }

    @Test
    void whenSearchFromOriginToDestInvalid_thenReturnStatus404WithStopNotFoundMessage() {
        when(stopService.getStopById(anyLong())).thenReturn(Optional.empty());

        String statusLine = RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("originId", "13213221").
                        param("destinationId", "33123212").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_NOT_FOUND).
                        body(is(Matchers.emptyOrNullString()))
                        .extract().statusLine();


        assertEquals("404 Stop 13213221 Not Found", statusLine);
        // it calls stopService 1 time instead of 2 because of java "short-circuiting"
        verify(stopService, times(1)).getStopById(anyLong());
        verify(tripService, times(0)).getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class));
    }

    @Test
    void whenSearchFromOriginToDestWithInvalidCurrency_thenReturnStatus404WithCurrencyNotFoundMessage() {
        when(stopService.getStopById(anyLong())).thenThrow(
                new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Currency doesntexist Not Found"));

        String statusLine = RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("originId", "1").
                        param("destinationId", "3").
                        param("currency", "doesntexist").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_NOT_FOUND).
                        body(is(Matchers.emptyOrNullString()))
                        .extract().statusLine();


        assertEquals("404 Currency doesntexist Not Found", statusLine);
        // it calls stopService 1 time because it is true on the first if
        verify(stopService, times(1)).getStopById(anyLong());
        verify(tripService, times(0)).getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class));
    }

    @Test
    void whenSearchFromOriginToDestValidWithoutDateAndCurrency_thenReturnListTripDetails() {
        when(stopService.getStopById(anyLong())).thenReturn(Optional.of(new Stop())); // not empty optional
        when(tripService.getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class)))
                .thenReturn(List.of(tripDetails_fromAveiro_toPorto_today));

        RestAssuredMockMvc.
        given().
                mockMvc(mockMvc).
                param("originId", "3").
                param("destinationId", "4").
        when().
                get("api/trips").
        then().
                statusCode(HttpStatus.SC_OK).
                body("size()", is(1)).
                body("id[0]", is(trip_fromAveiro_toPorto_today.getId().intValue()))
                .body("currency", Matchers.contains("EUR"));

        verify(stopService, times(2)).getStopById(anyLong());
        verify(tripService, times(1)).getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class));
    }

    @Test
    void whenSearchFromOriginToDestValidWithDateAndUSD_thenReturnList() {
        when(stopService.getStopById(anyLong())).thenReturn(Optional.of(new Stop())); // not empty optional
        when(tripService.getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class)))
                .thenReturn(List.of(tripDetails_fromLisboa_toPorto, tripDetails_fromLisboa_toBraga));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        param("originId", "1").
                        param("destinationId", "3").
                        param("departure_date", "2024-06-02").
                        param("currency", "USD").
                when().
                        get("api/trips").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("size()", is(2)).
                        body("id", Matchers.containsInAnyOrder(trip_fromLisboa_toPorto.getId().intValue(), trip_fromLisboa_toBraga.getId().intValue()))
                        .body("currency", Matchers.containsInAnyOrder("USD", "USD"));

        verify(stopService, times(2)).getStopById(anyLong());
        verify(tripService, times(1)).getAllTripsDetailsOnDate(anyLong(), anyLong(), anyString(), any(LocalDate.class));
    }

    @Test
    void whenGetTripWithValidId_thenReturnTripDetails() {
        when(tripService.getTripDetailsById(anyLong())).thenReturn(Optional.of(tripDetails_fromLisboa_toPorto));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/trips/{id}", "1").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("id", is(1));

        verify(stopService, times(0)).getStopById(anyLong());
        verify(tripService, times(1)).getTripDetailsById(anyLong());
    }

    @Test
    void whenGetTripWithInvalidId_thenReturnStatus404() {
        when(tripService.getTripDetailsById(anyLong())).thenReturn(Optional.empty());

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/trips/{id}", "113231221312").
                then().
                        statusCode(HttpStatus.SC_NOT_FOUND).
                        body(is(Matchers.emptyOrNullString()));

        verify(stopService, times(0)).getStopById(anyLong());
        verify(tripService, times(1)).getTripDetailsById(anyLong());
    }
}
