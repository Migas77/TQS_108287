package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import com.tqs108287.app.hw1_bustickets.dto.RatesDTO;
import com.tqs108287.app.hw1_bustickets.service.IRatesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RatesService implements IRatesService {

    private final RatesCache ratesCache;

    @Override
    public Optional<RatesDTO> getRatesFromEurTo(String toCurrency) {
        return Optional.empty();
    }

    @Override
    public RatesCacheMetricsDTO getRatesCacheMetrics() {
        return null;
    }
}
