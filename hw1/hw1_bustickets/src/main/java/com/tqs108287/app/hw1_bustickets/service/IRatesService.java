package com.tqs108287.app.hw1_bustickets.service;

import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import com.tqs108287.app.hw1_bustickets.dto.RatesDTO;

import java.util.Optional;

public interface IRatesService {
    // Default of API and my system is EUR
    Optional<RatesDTO> getRatesFromEurTo(String toCurrency);
    RatesCacheMetricsDTO getRatesCacheMetrics();
}
