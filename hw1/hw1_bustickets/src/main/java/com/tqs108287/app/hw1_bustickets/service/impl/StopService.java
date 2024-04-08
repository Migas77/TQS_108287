package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.repositories.StopRepository;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class StopService implements IStopService {

    static final Logger logger = getLogger(lookup().lookupClass());
    private StopRepository stopRepository;

    @Override
    public List<Stop> getAllStops() {
        logger.info("getAllStops()");
        return stopRepository.findAll();
    }

    @Override
    public Optional<Stop> getStopById(long id) {
        logger.info("getStopById(); arguments: id={}", id);
        return stopRepository.findById(id);
    }
}
