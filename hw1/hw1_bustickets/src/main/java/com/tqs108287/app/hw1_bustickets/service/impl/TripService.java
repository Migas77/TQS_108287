package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class TripService implements ITripService {

    static final Logger logger = getLogger(lookup().lookupClass());

    private TripRepository tripRepository;


    @Override
    public List<TripDetailsDTO> getAllTripsDetailsOnDate(long originId, long destinationId, LocalDate date) {
        logger.warn(String.valueOf(tripRepository.findById(destinationId).get().getReservationsCount()));
        logger.warn(String.valueOf(tripRepository.findById(destinationId).get().getReservations().size()));
        return tripRepository.findTripsBetweenStopsOnDate(originId, destinationId, date).stream()
                .map(TripDetailsDTO::fromTripEntity).toList();
    }

    @Override
    public Optional<Trip> getTripById(long tripId) {
        return tripRepository.findById(tripId);
    }

    @Override
    public Optional<TripDetailsDTO> getTripDetailsById(long tripId) {
        return tripRepository.findById(tripId).map(TripDetailsDTO::fromTripEntity);
    }
}
