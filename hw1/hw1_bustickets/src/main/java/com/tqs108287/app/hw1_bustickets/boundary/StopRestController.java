package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get All Stops")
    @ApiResponse(responseCode = "200", description = "Get all stops. You can later query for trips between this stops in trip-rest-controller.", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Stop.class)))})
    public ResponseEntity<List<Stop>> getAllStops(){
        logger.info("GET /api/stops");
        return ResponseEntity.ok(siteService.getAllStops());
    }
}
