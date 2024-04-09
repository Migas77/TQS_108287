package com.tqs108287.app.hw1_bustickets.UnitTest;

import com.tqs108287.app.hw1_bustickets.dto.ReservationDTO;
import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

class ReservationDTOTest {
    static final Logger logger = getLogger(lookup().lookupClass());

    @Test
    void givenValidReservationEntity_whenConvert_thenReturnDTO() {
        Trip trip_fromLisboa_toPorto = new Trip(11L, null, Set.of(), 50, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        Reservation reservation = new Reservation(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"), trip_fromLisboa_toPorto, 1, "Miguel", "Rua XPTO");

        ReservationDTO reservationDTO = ReservationDTO.fromReservationEntity(reservation);

        assertAll(
                () -> assertEquals(reservation.getTrip().getId(), reservationDTO.getTripId()),
                () -> assertEquals(reservation.getSeatNumber(), reservationDTO.getSeatNumber()),
                () -> assertEquals(reservation.getClientName(), reservationDTO.getClientName()),
                () -> assertEquals(reservation.getClientAddress(), reservationDTO.getClientAddress())
        );
    }

    @Test
    void givenInvalidReservationEntity_whenConvert_thenThrowException() {
        Reservation reservation = new Reservation(UUID.fromString("5fc03087-d265-11e7-b8c6-83e29cd24f4c"), null, 1, "Miguel", "Rua XPTO");

        assertThrows(NullPointerException.class, () -> ReservationDTO.fromReservationEntity(reservation));
    }

    @Test
    void givenReservationDTO_whenConvert_thenReturnReservationEntity(){
        Trip trip_fromLisboa_toPorto = new Trip(11L, null, Set.of(), 50, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        ReservationDTO reservationDTO = new ReservationDTO(trip_fromLisboa_toPorto.getId(), 1, "Cliente", "Morada");

        Reservation reservation = reservationDTO.toReservationEntity(trip_fromLisboa_toPorto);

        assertAll(
                () -> assertEquals(reservationDTO.getTripId(), reservation.getTrip().getId()),
                () -> assertEquals(reservationDTO.getSeatNumber(), reservation.getSeatNumber()),
                () -> assertEquals(reservationDTO.getClientName(), reservation.getClientName()),
                () -> assertEquals(reservationDTO.getClientAddress(), reservation.getClientAddress())
        );
    }
}
