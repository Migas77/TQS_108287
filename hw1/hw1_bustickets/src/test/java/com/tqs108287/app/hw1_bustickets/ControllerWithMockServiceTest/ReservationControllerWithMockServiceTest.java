package com.tqs108287.app.hw1_bustickets.ControllerWithMockServiceTest;

import com.tqs108287.app.hw1_bustickets.boundary.ReservationRestController;
import com.tqs108287.app.hw1_bustickets.dto.ReservationDTO;
import com.tqs108287.app.hw1_bustickets.entities.*;
import com.tqs108287.app.hw1_bustickets.service.IReservationService;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.slf4j.LoggerFactory.getLogger;

@WebMvcTest(ReservationRestController.class)
public class ReservationControllerWithMockServiceTest {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ITripService tripService;

    @MockBean
    private IReservationService reservationService;

    Trip trip_fromLisboa_toPorto;
    Reservation reservation_fromLisboa_toPorto;
    ReservationDTO reservationDTO_fromLisboa_toPorto;

    // TODO CHANGE BeforeEach later
    @BeforeEach
    void setup(){
        Stop stop_lisboa = new Stop(12L, "Lisboa", "Lisboa");
        Stop stop_coimbra = new Stop(21L, "Coimbra", "Coimbra");
        Stop stop_aveiro = new Stop(32L, "Aveiro", "Aveiro");
        Stop stop_porto = new Stop(4L, "Porto", "Porto");
        Leg leg_fromLisboa_toCoimbra = new Leg(11L, stop_lisboa, stop_coimbra, LocalTime.of(0, 23));
        Leg leg_fromCoimbra_toAveiro = new Leg(3L, stop_coimbra, stop_aveiro, LocalTime.of(0, 40));
        Leg leg_fromAveiro_toPorto = new Leg(52L, stop_aveiro, stop_porto, LocalTime.of(0, 33));
        Route route_fromLisboa_toPorto = new Route(13L, List.of(leg_fromLisboa_toCoimbra, leg_fromCoimbra_toAveiro, leg_fromAveiro_toPorto));
        trip_fromLisboa_toPorto = new Trip(11L, route_fromLisboa_toPorto, 50, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        reservation_fromLisboa_toPorto = new Reservation(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"),trip_fromLisboa_toPorto, 1, "Miguel", "Rua XPTO");
        reservationDTO_fromLisboa_toPorto = ReservationDTO.fromReservationEntity(reservation_fromLisboa_toPorto);
    }

    @Test
    void givenInvalidTrip_whenMakeReservation_thenReturnStatus404() {
        when(tripService.getTripById(anyLong())).thenReturn(Optional.empty());

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        contentType(ContentType.JSON).
                        body(reservationDTO_fromLisboa_toPorto).
                when().
                        post("api/reservations").
                then().
                        statusCode(HttpStatus.SC_NOT_FOUND).
                        body(is(Matchers.emptyOrNullString()));

        verify(tripService, times(1)).getTripById(anyLong());
        verify(reservationService, times(0)).makeReservation(any(Trip.class));
    }

    @Test
    void givenValidFullTrip_whenMakeReservation_thenReturnStatus409() {
        // same test scenario as booking an already chosen seat
        // as that condition is evaluated inside makeReservation

        when(tripService.getTripById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto));
        when(reservationService.makeReservation(any(Trip.class))).thenReturn(Optional.empty());

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        contentType(ContentType.JSON).
                        body(reservationDTO_fromLisboa_toPorto).
                when().
                        post("api/reservations").
                then().
                        statusCode(HttpStatus.SC_CONFLICT).
                        body(is(Matchers.emptyOrNullString()));

        verify(tripService, times(1)).getTripById(anyLong());
        verify(reservationService, times(1)).makeReservation(any(Trip.class));
    }


    @Test
    void givenValidNotFullTrip_whenMakeReservation_thenReturnReservation() {
        when(tripService.getTripById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto));
        when(reservationService.makeReservation(any(Trip.class))).thenReturn(Optional.of(reservation_fromLisboa_toPorto));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        contentType(ContentType.JSON).
                        body(reservationDTO_fromLisboa_toPorto).
                when().
                        post("api/reservations").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("trip.id", is(trip_fromLisboa_toPorto.getId().intValue()));

        verify(tripService, times(1)).getTripById(anyLong());
        verify(reservationService, times(1)).makeReservation(any(Trip.class));
    }

    @Test
    void givenValidUUID_whenSearchReservation_thenReturnReservation() {
        when(reservationService.getReservationById(any(UUID.class))).thenReturn(Optional.of(reservation_fromLisboa_toPorto));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/reservations/5fc03087-d265-11e7-b8c6-83e29cd24f4c").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("trip.id", is(trip_fromLisboa_toPorto.getId().intValue()));

        verify(reservationService, times(1)).getReservationById(any(UUID.class));
    }

    @Test
    void givenInvalidUUID_whenSearchReservation_thenReturnStatus404() {
        when(reservationService.getReservationById(any(UUID.class))).thenReturn(Optional.empty());

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/reservations/5fffffff-d265-11e7-b8c6-83e29cd24f4c").
                then().
                        statusCode(HttpStatus.SC_NOT_FOUND).
                        body(is(Matchers.emptyOrNullString()));

        verify(reservationService, times(1)).getReservationById(any(UUID.class));
    }
}
