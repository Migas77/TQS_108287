package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.repositories.StopRepository;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StopService implements IStopService {

    private StopRepository stopRepository;

    @Override
    public List<Stop> getAllStops() {
        return List.of();
    }

    @Override
    public Optional<Stop> getStopById(long id) {
        return stopRepository.findById(id);
    }
}
