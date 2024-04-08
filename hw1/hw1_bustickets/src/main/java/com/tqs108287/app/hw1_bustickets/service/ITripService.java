package com.tqs108287.app.hw1_bustickets.service;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.Trip;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITripService {
    public List<TripDetailsDTO> getAllTripsDetailsOnDate(long originId, long destinationId, LocalDate date);

    public Optional<Trip> getTripById(long tripId);

    public Optional<TripDetailsDTO> getTripDetailsById(long tripId);

    public long getNumberReservationsByTrip(Trip trip);
}
