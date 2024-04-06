package com.tqs108287.app.hw1_bustickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RatesCacheMetricsDTO {
    long cacheHits;
    long cacheMisses;
}
