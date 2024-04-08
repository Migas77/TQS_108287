package com.tqs108287.app.hw1_bustickets.UnitTest;


import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import java.util.concurrent.TimeUnit;
import static java.lang.invoke.MethodHandles.lookup;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.slf4j.LoggerFactory.getLogger;

class RatesCacheTest {

    static final Logger logger = getLogger(lookup().lookupClass());
    RatesCache ratesCache;

    @BeforeEach
    void setup(){
        ratesCache = new RatesCache();
    }

    @Test
    void testGetAfterPut() {
        ratesCache.put("USD", 10f);
        assertEquals(10f, ratesCache.get("USD"));
        assertEquals(1, ratesCache.getMetrics().getCacheHits());
        assertEquals(0, ratesCache.getMetrics().getCacheMisses());
    }

    @Test
    void testGetNoPut() {
        assertNull(ratesCache.get("USD"));
        assertEquals(0, ratesCache.getMetrics().getCacheHits());
        assertEquals(1, ratesCache.getMetrics().getCacheMisses());
    }

    @Test
    void testGetAfterTtl(){
        ratesCache.setTtl(1);
        ratesCache.put("ALL", 15f);

        // Compliant with sonar (instead of sleep)
        await().atMost(2, TimeUnit.SECONDS).until(() -> ratesCache.get("ALL") == null);
        assertNull(ratesCache.get("ALL"));
        assertEquals(2, ratesCache.getMetrics().getCacheMisses());
    }

    @Test
    void testGetMetrics() {
        ratesCache.get("naoexiste");
        ratesCache.get("naoexiste");
        ratesCache.get("naoexiste");
        ratesCache.put("USD", 10f);
        ratesCache.get("USD");

        assertEquals(1, ratesCache.getMetrics().getCacheHits());
        assertEquals(3, ratesCache.getMetrics().getCacheMisses());
    }
}
