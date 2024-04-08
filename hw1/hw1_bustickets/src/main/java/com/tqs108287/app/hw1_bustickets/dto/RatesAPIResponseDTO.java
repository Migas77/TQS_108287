package com.tqs108287.app.hw1_bustickets.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class RatesAPIResponseDTO {
    float amount;
    String base;
    String date;
    Map<String, Float> rates;
}
