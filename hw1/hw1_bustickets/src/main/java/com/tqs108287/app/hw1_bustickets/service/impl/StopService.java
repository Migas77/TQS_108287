package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StopService implements IStopService {
    @Override
    public List<Stop> getAllStops() {
        return List.of();
    }
}
