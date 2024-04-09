package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.IRatesService;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get All Trips between two stops, with prices on a certain currency and with a specific date.")
    @ApiResponse(responseCode = "200", description = "Successful attempt that culminates with returning a list of the specified trips. (can be empty list)", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TripDetailsDTO.class)))})
    @ApiResponse(responseCode = "404", description = "Unsuccessful attempt in searching for trips due to non existing stop (origin or destination) or currency not being found. Response is handled by spring and contains a message equal to 'Stop x Not Found' or 'Currency x Not Found' depending on the case. ", content = @Content(schema = @Schema(implementation = Void.class)))
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

    @Operation(summary = "Get Details of a trip by its id.")
    @ApiResponse(responseCode = "200", description = "Successful attempt - exists a trip with the specified id.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TripDetailsDTO.class))})
    @ApiResponse(responseCode = "404", description = "Unsuccessful attempt - the trip with the specified id doesn't exist.", content = @Content(schema = @Schema(implementation = Void.class)))
    @GetMapping("{id}")
    public ResponseEntity<TripDetailsDTO> getTripDetailsById(@PathVariable Long id){
        logger.info("GET /api/trips/{}", id);

        Optional<TripDetailsDTO> tripOpt = tripService.getTripDetailsById(id);
        return tripOpt.map(ResponseEntity::ok).orElseGet(() -> {
            logger.info("Reservation with id {} not found.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

}
