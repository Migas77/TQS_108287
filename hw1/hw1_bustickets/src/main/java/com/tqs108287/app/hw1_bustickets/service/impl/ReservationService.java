package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.IReservationService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService implements IReservationService {
    @Override
    public Optional<Reservation> makeReservation(Trip trip) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> getReservationById(UUID id) {
        return Optional.empty();
    }
}
