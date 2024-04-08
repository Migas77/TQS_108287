package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
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
    public ResponseEntity<RatesCacheMetricsDTO> getRatesCacheMetrics(){
        logger.info("GET /api/cache");
        return ResponseEntity.ok(ratesCache.getMetrics());
    }
}
