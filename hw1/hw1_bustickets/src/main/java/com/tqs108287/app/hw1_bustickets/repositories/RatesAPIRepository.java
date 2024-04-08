package com.tqs108287.app.hw1_bustickets.repositories;

import com.tqs108287.app.hw1_bustickets.dto.RatesAPIResponseDTO;
import com.tqs108287.app.hw1_bustickets.dto.RatesDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RatesAPIRepository {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String BASE_URL = "https://api.frankfurter.app/";
    private final RestTemplate restTemplate;

    public Optional<RatesDTO> getRatesFromEurToCurrency(String currency){
        try {
            ResponseEntity<RatesAPIResponseDTO> response =
                    restTemplate.getForEntity(BASE_URL + "latest?from=EUR&to=" + currency, RatesAPIResponseDTO.class);

            RatesAPIResponseDTO ratesAPIResponseDTO = response.getBody();
            if (ratesAPIResponseDTO == null) {
                return Optional.empty();
            }

            return Optional.of(new RatesDTO(currency, ratesAPIResponseDTO.getRates().get(currency)));
        }catch (RestClientException e){
            return Optional.empty();
        }
    }

}
