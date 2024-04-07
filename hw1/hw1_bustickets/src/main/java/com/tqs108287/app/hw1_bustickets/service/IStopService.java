package com.tqs108287.app.hw1_bustickets.service;

import com.tqs108287.app.hw1_bustickets.entities.Stop;

import java.util.List;
import java.util.Optional;

public interface IStopService {
    List<Stop> getAllStops();
    Optional<Stop> getStopById(long id);
}
