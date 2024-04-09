package com.tqs108287.app.hw1_bustickets.UnitTest;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Route;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

public class TripDetailsDTOTest {
    static final Logger logger = getLogger(lookup().lookupClass());

    @Test
    void givenValidTripEntity_whenConvert_thenReturnDTO() {
        Reservation r1 = new Reservation();
        r1.setSeatNumber(2);
        Reservation r2 = new Reservation();
        r2.setSeatNumber(4);
        Trip trip_fromLisboa_toPorto = new Trip(11L, new Route(), Set.of(r1, r2),
                7, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));

        TripDetailsDTO tripDetailsDTO = TripDetailsDTO.fromTripEntity(trip_fromLisboa_toPorto);

        assertAll(
                () -> assertEquals(trip_fromLisboa_toPorto.getId(), tripDetailsDTO.getId()),
                () -> assertEquals(trip_fromLisboa_toPorto.getRoute(), tripDetailsDTO.getRoute()),
                () -> assertEquals(List.of(1,3,5,6,7), tripDetailsDTO.getAvailableSeats()),
                () -> assertEquals(trip_fromLisboa_toPorto.getNumberOfSeats(), tripDetailsDTO.getNumberOfSeats()),
                () -> assertEquals(trip_fromLisboa_toPorto.getPriceEuros(), tripDetailsDTO.getPrice()),
                () -> assertEquals("EUR", tripDetailsDTO.getCurrency()),
                () -> assertEquals(trip_fromLisboa_toPorto.getDepartureTime(), tripDetailsDTO.getDepartureTime())
        );
    }

    @Test
    void givenInvalidTripEntity_whenConvert_thenReturnDTO() {
        Trip trip_fromLisboa_toPorto = new Trip(11L, new Route(), null,
                7, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));

        assertThrows(NullPointerException.class, () -> TripDetailsDTO.fromTripEntity(trip_fromLisboa_toPorto));
    }
}
