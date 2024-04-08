package com.tqs108287.app.hw1_bustickets.components;

import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@Component
@NoArgsConstructor
public class RatesCache {
    static final Logger logger = getLogger(lookup().lookupClass());
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
            logger.info("CacheMiss for {}", currency);
            cacheMisses.incrementAndGet();
        } else {
            logger.info("CacheHit for {}", currency);
            cacheHits.incrementAndGet();
        }
        return value;
    }

    public void put(String currency, Float value){
        ratesMap.put(currency, value);
        threadPool.schedule(() -> {
            logger.info("ttl expired - removing {}", currency);
            ratesMap.remove(currency);
        }, ttl, TimeUnit.SECONDS);
    }

    public RatesCacheMetricsDTO getMetrics(){
        logger.info("getMetrics()");
        return new RatesCacheMetricsDTO(cacheHits.get(), cacheMisses.get());
    }

}
