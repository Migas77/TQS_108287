package com.tqs108287.app.hw1_bustickets.ServiceTest;

import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import com.tqs108287.app.hw1_bustickets.dto.RatesDTO;
import com.tqs108287.app.hw1_bustickets.repositories.RatesAPIRepository;
import com.tqs108287.app.hw1_bustickets.service.impl.RatesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatesServiceWithMockAPIRepositoryTest {

    @Mock
    RatesAPIRepository ratesAPIRepository;

    @Mock
    RatesCache ratesCache;

    @InjectMocks
    private RatesService ratesService;


    @Test
    void givenCacheMetrics_whenGetCacheMetrics_thenReturnCacheMetrics() {
        when(ratesCache.getMetrics()).thenReturn(new RatesCacheMetricsDTO(25, 1));

        RatesCacheMetricsDTO ratesCacheMetricsDTO = ratesService.getRatesCacheMetrics();

        assertThat(ratesCacheMetricsDTO).extracting(RatesCacheMetricsDTO::getCacheHits, RatesCacheMetricsDTO::getCacheMisses)
                        .containsExactly(25L, 1L);
        verify(ratesCache, times(1)).getMetrics();
    }

    @Test
    void givenRates_whenGetRatesFromEURToInvalidCurrency_thenDontCallRepoAndReturnEmptyOptional() {
        when(ratesCache.get(anyString())).thenReturn(null);
        when(ratesAPIRepository.getRatesFromEurToCurrency(anyString())).thenReturn(Optional.empty());

        Optional<RatesDTO> ratesDTOOpt = ratesService.getRatesFromEurTo("dkaldksa");

        assertThat(ratesDTOOpt).isEmpty();
        verify(ratesCache, times(1)).get(anyString());
        verify(ratesAPIRepository, times(1)).getRatesFromEurToCurrency(anyString());
        verify(ratesCache, times(0)).put(anyString(), anyFloat());
    }

    @Test
    void givenRates_whenGetRatesFromEURToNonCachedValidCurrency_thenCallRepoAndReturnOptionalOfRatesDTO() {
        RatesDTO ratesDTO = new RatesDTO("USD", 1.1f);
        when(ratesCache.get(anyString())).thenReturn(null);
        when(ratesAPIRepository.getRatesFromEurToCurrency(anyString())).thenReturn(Optional.of(ratesDTO));

        Optional<RatesDTO> ratesDTOOpt = ratesService.getRatesFromEurTo("USD");

        assertThat(ratesDTOOpt).isPresent().hasValue(ratesDTO);
        verify(ratesCache, times(1)).get(anyString());
        verify(ratesAPIRepository, times(1)).getRatesFromEurToCurrency(anyString());
        verify(ratesCache, times(1)).put(anyString(), anyFloat());

    }

    @Test
    void givenRates_whenGetRatesFromEURToCachedValidCurrency_thenDontCallRepoAndReturnOptionalOfRatesDTO() {
        when(ratesCache.get(anyString())).thenReturn(1.1f);

        Optional<RatesDTO> ratesDTOOpt = ratesService.getRatesFromEurTo("USD");

        assertThat(ratesDTOOpt).isPresent().hasValueSatisfying(
                ratesDTO -> assertThat(ratesDTO).extracting(RatesDTO::getCurrency, RatesDTO::getRate)
                        .containsExactly("USD", 1.1f)
        );
        verify(ratesCache, times(1)).get(anyString());
        verify(ratesAPIRepository, times(0)).getRatesFromEurToCurrency(anyString());
        verify(ratesCache, times(0)).put(anyString(), anyFloat());

    }
}
