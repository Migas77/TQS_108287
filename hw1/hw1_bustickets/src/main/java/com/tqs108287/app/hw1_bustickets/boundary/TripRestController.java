package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.IRatesService;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;


@RestController
@RequestMapping("/api/trips")
@AllArgsConstructor
public class TripRestController {

    static final Logger logger = getLogger(lookup().lookupClass());
    private final ITripService tripService;
    private final IStopService stopService;

    @GetMapping
    public ResponseEntity<List<TripDetailsDTO>> searchTrips(
            @RequestParam Long originId,
            @RequestParam Long destinationId,
            @RequestParam(defaultValue = "EUR") String currency,
            @RequestParam(name = "departure_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> departureDateOpt
    ){
        logger.info("GET /api/trips; arguments: origin_id={}; destinationId={}; currency={}; departureDateOpt.isEmpty={} ",
                originId, destinationId, currency, departureDateOpt.isEmpty());

        LocalDate departureDate = departureDateOpt.orElseGet(LocalDate::now);

        if(stopService.getStopById(originId).isEmpty()){
            String errorMessage = String.format("Stop %d Not Found", originId);
            logger.info(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        if(stopService.getStopById(destinationId).isEmpty()){
            String errorMessage = String.format("Stop %d Not Found", destinationId);
            logger.info(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        List<TripDetailsDTO> trips = tripService.getAllTripsDetailsOnDate(originId, destinationId, currency, departureDate);

        return ResponseEntity.ok(trips);
    }

    @GetMapping("{id}")
    public ResponseEntity<Trip> getTripDetailsById(@PathVariable Long id){
        logger.info("GET /api/trips/{}", id);

        Optional<Trip> tripOpt = tripService.getTripById(id);
        return tripOpt.map(ResponseEntity::ok).orElseGet(() -> {
            logger.info("Reservation with id {} not found.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

}
