package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Trip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripRestController {

//    private final TicketManagerService ticketManagerService;
//
//    public TripRestController(TicketManagerService ticketManagerService) {
//        this.ticketManagerService = ticketManagerService;
//    }

    @GetMapping
    public ResponseEntity<List<Trip>> searchTrips(
         @RequestParam Long originId,
         @RequestParam Long destinationId,
         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ){
         return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<Trip> getTripDetailsById(@PathVariable Long id){
        return null;
    }

}
