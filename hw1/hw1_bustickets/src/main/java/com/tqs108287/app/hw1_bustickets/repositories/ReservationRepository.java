package com.tqs108287.app.hw1_bustickets.repositories;

import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByTripAndSeatNumber(Trip trip, int seatNumber);

    Optional<Reservation> findById(UUID id);
}
