package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationRestController {

    @GetMapping("{id}")
    public ResponseEntity<Reservation> getReservationDetailsById(@PathVariable UUID id){
        return null;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(){
        return null;
    }

}
