package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TripService implements ITripService {


    private TripRepository tripRepository;
    private IStopService stopService;



    @Override
    public List<Trip> getAllTripsOnDate(long originId, long destinationId, LocalDate date) {
        Optional<Stop> originStopOpt = stopService.getStopById(originId);
        Optional<Stop> destinationStopOpt = stopService.getStopById(destinationId);
        if (originStopOpt.isEmpty() || destinationStopOpt.isEmpty())
            return List.of();

        // findTripsByRoute(Route.find)

        return List.of();

    }

    @Override
    public Optional<Trip> getTripById(long tripId) {
        return Optional.empty();
    }
}
