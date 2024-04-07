package com.tqs108287.app.hw1_bustickets.ServiceTest;

import com.tqs108287.app.hw1_bustickets.dto.ReservationDTO;
import com.tqs108287.app.hw1_bustickets.entities.*;
import com.tqs108287.app.hw1_bustickets.repositories.ReservationRepository;
import com.tqs108287.app.hw1_bustickets.service.impl.ReservationService;
import com.tqs108287.app.hw1_bustickets.service.impl.TripService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.slf4j.LoggerFactory.getLogger;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceWithMockRepositoryTest {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    TripService tripService;

    @InjectMocks
    ReservationService reservationService;
    
    Trip trip_fromLisboa_toPorto_1;
    Trip trip_fromLisboa_toPorto_2;
    Reservation reservation_fromLisboa_toPorto;

    
    // TODO CHANGE THIS
    @BeforeEach
    void setup(){
        Stop stop_lisboa = new Stop(1L, "Lisboa", "Lisboa");
        Stop stop_coimbra = new Stop(2L, "Coimbra", "Coimbra");
        Stop stop_aveiro = new Stop(3L, "Aveiro", "Aveiro");
        Stop stop_porto = new Stop(4L, "Porto", "Porto");
        Leg leg_fromLisboa_toCoimbra = new Leg(1L, stop_lisboa, stop_coimbra, LocalTime.of(2, 2));
        Leg leg_fromCoimbra_toAveiro = new Leg(3L, stop_coimbra, stop_aveiro, LocalTime.of(0, 48));
        Leg leg_fromAveiro_toPorto = new Leg(5L, stop_aveiro, stop_porto, LocalTime.of(0, 48));
        Route route_fromLisboa_toPorto = new Route(1L, List.of(leg_fromLisboa_toCoimbra, leg_fromCoimbra_toAveiro, leg_fromAveiro_toPorto));
        trip_fromLisboa_toPorto_1 = new Trip(1L, route_fromLisboa_toPorto, Set.of(), 2, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        trip_fromLisboa_toPorto_2 = new Trip(2L, route_fromLisboa_toPorto, Set.of(), 1, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        reservation_fromLisboa_toPorto = new Reservation(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                trip_fromLisboa_toPorto_1, 1, "Cliente", "Morada");
        trip_fromLisboa_toPorto_1.setReservations(Set.of(reservation_fromLisboa_toPorto));
        trip_fromLisboa_toPorto_2.setReservations(Set.of(new Reservation(UUID.fromString("124e4567-e89b-12d3-a456-426614174000"),
                trip_fromLisboa_toPorto_2, 1, "Cliente", "Morada")));
    }

    @Test
    void givenManyReservations_whenGetReservationByInvalidId_thenReturnEmptyOptional() {
        Optional<Reservation> tripOpt = reservationService.getReservationById(UUID.fromString("122e4567-e89b-12d3-a456-426614174000"));

        assertThat(tripOpt).isEmpty();
        verify(reservationRepository, times(0)).findById(anyLong());
        verify(tripService, times(0)).getTripById(anyLong());
    }

    @Test
    void givenManyReservations_whenGetReservationByValidId_thenReturnOptionalOfReservation(){
        when(reservationRepository.findById(any(UUID.class))).thenReturn(Optional.of(reservation_fromLisboa_toPorto));

        Optional<Reservation> tripOpt = reservationService.getReservationById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        assertThat(tripOpt)
                .isNotEmpty()
                .hasValueSatisfying(reservation -> assertThat(reservation.getId()).isEqualTo(reservation_fromLisboa_toPorto.getId()));
        verify(reservationRepository, times(1)).findById(any(UUID.class));
        verify(tripService, times(0)).getTripById(anyLong());
    }

    @Test
    void givenManyReservations_whenReserveInvalidSeat_thenReturnEmptyOptional() {
        ReservationDTO reservationDTO_fromLisboa_toPorto = new ReservationDTO(trip_fromLisboa_toPorto_1.getId(),
                10, "Cliente", "Morada");
        when(tripService.getTripById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto_1));

        Optional<Reservation> tripOpt = reservationService.makeReservation(reservationDTO_fromLisboa_toPorto);

        logger.info("aqui ze");

        assertThat(tripOpt).isEmpty();
        verify(reservationRepository, times(0)).save(any(Reservation.class));
        verify(tripService, times(1)).getTripById(anyLong());
    }

    @Test
    void givenManyReservations_whenReserveValidSeatFullBus_thenReturnEmptyOptional() {
        ReservationDTO reservationDTO_fromLisboa_toPorto = new ReservationDTO(trip_fromLisboa_toPorto_2.getId(),
                1, "Cliente", "Morada");
        when(tripService.getTripById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto_2));

        Optional<Reservation> tripOpt = reservationService.makeReservation(reservationDTO_fromLisboa_toPorto);

        assertThat(tripOpt).isEmpty();
        verify(reservationRepository, times(0)).save(any(Reservation.class));
        verify(tripService, times(1)).getTripById(anyLong());
    }

    @Test
    void givenManyReservations_whenReserveOccupiedSeatNotFullBus_thenReturnEmptyOptional() {
        ReservationDTO reservationDTO_fromLisboa_toPorto = new ReservationDTO(trip_fromLisboa_toPorto_1.getId(),
                1, "Cliente", "Morada");
        when(tripService.getTripById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto_1));

        Optional<Reservation> tripOpt = reservationService.makeReservation(reservationDTO_fromLisboa_toPorto);

        assertThat(tripOpt).isEmpty();
        verify(reservationRepository, times(0)).save(any(Reservation.class));
        verify(tripService, times(1)).getTripById(anyLong());
    }

    @Test
    void givenManyReservations_whenReserveEmptySeatNotFullBus_thenReturnOptionalOfReservation(){
        ReservationDTO reservationDTO_fromLisboa_toPorto = new ReservationDTO(trip_fromLisboa_toPorto_1.getId(),
                2, "Cliente", "Morada");
        when(tripService.getTripById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto_1));

        Optional<Reservation> tripOpt = reservationService.makeReservation(reservationDTO_fromLisboa_toPorto);

        assertThat(tripOpt)
                .isNotEmpty()
                .hasValueSatisfying(reservation -> {
                    assertThat(reservation.getId()).isEqualTo(reservation_fromLisboa_toPorto.getId());
                    assertThat(reservation.getTrip()).isNotNull();
                    assertThat(reservation.getTrip().getId()).isEqualTo(trip_fromLisboa_toPorto_1.getId());
                });
        verify(reservationRepository, times(0)).save(any(Reservation.class));
        verify(tripService, times(1)).getTripById(anyLong());
    }

    @Test
    void givenManyReservations_whenReserveInvalidTrip_thenReturnOptionalOfReservation(){
        ReservationDTO reservationDTO_fromLisboa_toPorto = new ReservationDTO(134L,
                2, "Cliente", "Morada");
        when(tripService.getTripById(anyLong())).thenReturn(Optional.empty());

        Optional<Reservation> tripOpt = reservationService.makeReservation(reservationDTO_fromLisboa_toPorto);

        assertThat(tripOpt).isEmpty();
        verify(reservationRepository, times(0)).save(any(Reservation.class));
        verify(tripService, times(1)).getTripById(anyLong());
    }

    // check if busIsFull function is working properly

}
