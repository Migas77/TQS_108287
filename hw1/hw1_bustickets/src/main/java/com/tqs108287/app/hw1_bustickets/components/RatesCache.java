package com.tqs108287.app.hw1_bustickets.components;

import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
@NoArgsConstructor
public class RatesCache {
    private final Map<String, Float> ratesMap = new HashMap<>();
    private final AtomicLong cacheHits = new AtomicLong(0L);
    private final AtomicLong cacheMisses = new AtomicLong(0L);
    private final ScheduledExecutorService threadPool = new ScheduledThreadPoolExecutor(2);

    @Getter
    @Setter
    private int ttl = 300;

    public RatesCache(int ttl) {
        this.ttl = ttl;
    }

    public Float get(String currency){
        Float value = ratesMap.getOrDefault(currency, null);
        if (value == null){
            cacheMisses.incrementAndGet();
        } else {
            cacheHits.incrementAndGet();
        }
        return value;
    }

    public void put(String currency, Float value){
        ratesMap.put(currency, value);
        threadPool.schedule(() -> {ratesMap.remove(currency);}, ttl, TimeUnit.SECONDS);
    }

    public RatesCacheMetricsDTO getMetrics(){
        return new RatesCacheMetricsDTO(cacheHits.get(), cacheMisses.get());
    }

    public void reset(){
        cacheHits.set(0);
        cacheMisses.set(0);
        ratesMap.clear();
    }
}
