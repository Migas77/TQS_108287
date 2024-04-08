package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import com.tqs108287.app.hw1_bustickets.dto.RatesDTO;
import com.tqs108287.app.hw1_bustickets.repositories.RatesAPIRepository;
import com.tqs108287.app.hw1_bustickets.service.IRatesService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class RatesService implements IRatesService {

    static final Logger logger = getLogger(lookup().lookupClass());
    private final RatesAPIRepository ratesAPIRepository;
    private final RatesCache ratesCache;

    @Override
    public Optional<RatesDTO> getRatesFromEurTo(String toCurrency) {
        logger.info("getRatesFromEuroTo(); arguments: toCurrency={}", toCurrency);
        Optional<RatesDTO> ratesDTOOpt;
        Float rate = ratesCache.get(toCurrency);
        if (rate == null){
            logger.info("Currency {} not cached - calling APIRepository", toCurrency);
            ratesDTOOpt = ratesAPIRepository.getRatesFromEurToCurrency(toCurrency);
            if (ratesDTOOpt.isPresent()){
                RatesDTO ratesDTO = ratesDTOOpt.get();
                ratesCache.put(ratesDTO.getCurrency(), ratesDTO.getRate());
            }
        } else {
            logger.info("Currency {} cached - not calling APIRepository", toCurrency);
            ratesDTOOpt = Optional.of(new RatesDTO(toCurrency, rate));
        }
        return ratesDTOOpt;
    }

    @Override
    public RatesCacheMetricsDTO getRatesCacheMetrics() {
        logger.info("getRatesCacheMetrics()");
        return ratesCache.getMetrics();
    }
}
