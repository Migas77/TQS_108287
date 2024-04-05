package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TripService implements ITripService {
    @Override
    public List<Trip> getAllTripsOnDate(long originId, long destinationId, LocalDate date) {
        return null;
    }

    @Override
    public Optional<Trip> getTripById(long tripId) {
        return Optional.empty();
    }
}
