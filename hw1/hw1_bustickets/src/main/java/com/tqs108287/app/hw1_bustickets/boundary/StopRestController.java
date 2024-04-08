package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/stops")
@AllArgsConstructor
public class StopRestController {

    static final Logger logger = getLogger(lookup().lookupClass());
    private final IStopService siteService;

    @GetMapping
    public ResponseEntity<List<Stop>> getAllStops(){
        logger.info("GET /api/stops");
        return ResponseEntity.ok(siteService.getAllStops());
    }
}
