package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ResponseEntity<List<Trip>> searchTrips(
            @RequestParam Long originId,
            @RequestParam Long destinationId,
            @RequestParam(name = "departure_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> departureDateOpt
    ){
        LocalDate departureDate = departureDateOpt.orElseGet(LocalDate::now);

        if (stopService.getStopById(originId).isEmpty() || stopService.getStopById(destinationId).isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(tripService.getAllTripsOnDate(originId, destinationId, departureDate));
    }

    @GetMapping("{id}")
    public ResponseEntity<Trip> getTripDetailsById(@PathVariable Long id){
        Optional<Trip> tripOpt = tripService.getTripById(id);
        return tripOpt.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
