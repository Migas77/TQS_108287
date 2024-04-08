package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.dto.ReservationDTO;
import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.IReservationService;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/reservations")
@AllArgsConstructor
public class ReservationRestController {

    static final Logger logger = getLogger(lookup().lookupClass());
    private final ITripService tripService;
    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservationDTO){
        logger.info("createReservation; arguments={}", reservationDTO);
        Optional<Trip> tripOpt = tripService.getTripById(reservationDTO.getTripId());
        if (tripOpt.isEmpty()){
            logger.info("Trip with id {} not found", reservationDTO.getTripId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return reservationService.makeReservation(reservationDTO).map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @GetMapping("{id}")
    public ResponseEntity<Reservation> getReservationDetailsById(@PathVariable UUID id){
        logger.info("getReservationDetailsById; arguments: id={}", id);
        Optional<Reservation> reservationOpt = reservationService.getReservationById(id);
        return reservationOpt.map(ResponseEntity::ok).orElseGet(() -> {
            logger.info("Reservation with uuid {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

}
