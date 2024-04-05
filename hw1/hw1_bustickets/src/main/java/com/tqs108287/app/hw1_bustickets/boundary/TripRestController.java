package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/trips")
@AllArgsConstructor
public class TripRestController {

    private final ITripService tripService;

    @GetMapping
    public ResponseEntity<List<Trip>> searchTrips(
            @RequestParam Long originId,
            @RequestParam Long destinationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> departureDateOpt
    ){
        LocalDate departureDate = departureDateOpt.orElseGet(LocalDate::now);
        return ResponseEntity.ok(tripService.getAllTripsOnDate(originId, destinationId, departureDate));
    }

    @GetMapping("{id}")
    public ResponseEntity<Trip> getTripDetailsById(@PathVariable Long id){
        Optional<Trip> tripOpt = tripService.getTripById(id);
        return tripOpt.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
