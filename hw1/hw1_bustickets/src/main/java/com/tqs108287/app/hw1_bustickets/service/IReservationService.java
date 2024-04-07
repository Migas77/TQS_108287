package com.tqs108287.app.hw1_bustickets.service;

import com.tqs108287.app.hw1_bustickets.dto.ReservationDTO;
import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;

import java.util.Optional;
import java.util.UUID;

public interface IReservationService {
    Optional<Reservation> makeReservation(ReservationDTO reservationDTO);
    Optional<Reservation> getReservationById(UUID id);
}
