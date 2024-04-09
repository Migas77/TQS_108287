package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/cache")
@AllArgsConstructor
public class RatesCacheRestController {

    static final Logger logger = getLogger(lookup().lookupClass());
    private RatesCache ratesCache;

    @GetMapping
    @Operation(summary = "Get Cache Metrics of the currency rates cache.")
    @ApiResponse(responseCode = "200", description = "Cache Metrics of the currency rates cache: hits and misses.", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RatesCacheMetricsDTO.class))})
    public ResponseEntity<RatesCacheMetricsDTO> getRatesCacheMetrics(){
        logger.info("GET /api/cache");
        return ResponseEntity.ok(ratesCache.getMetrics());
    }
}
