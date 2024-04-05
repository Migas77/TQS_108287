package com.tqs108287.app.hw1_bustickets.service;

import com.tqs108287.app.hw1_bustickets.entities.Trip;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface ITripService {
    public List<Trip> getAllTripsOnDate(long originId, long destinationId, LocalDate date);
}
