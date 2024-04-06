package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
@AllArgsConstructor
public class RatesCacheRestController {

    private RatesCache ratesCache;

    @GetMapping
    public ResponseEntity<RatesCacheMetricsDTO> getRatesCacheMetrics(){
        return ResponseEntity.ok(ratesCache.getMetrics());
    }
}
