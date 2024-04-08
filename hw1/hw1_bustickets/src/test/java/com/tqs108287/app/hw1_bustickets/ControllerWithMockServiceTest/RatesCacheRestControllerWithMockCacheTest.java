package com.tqs108287.app.hw1_bustickets.ControllerWithMockServiceTest;

import com.tqs108287.app.hw1_bustickets.boundary.RatesCacheRestController;
import com.tqs108287.app.hw1_bustickets.components.RatesCache;
import com.tqs108287.app.hw1_bustickets.dto.RatesCacheMetricsDTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.slf4j.LoggerFactory.getLogger;

@WebMvcTest(RatesCacheRestController.class)
class RatesCacheRestControllerWithMockCacheTest {
    static final Logger logger = getLogger(lookup().lookupClass());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RatesCache ratesCache;

    @Test
    void givenCache_whenGetMetrics_ReturnCacheMetrics() {
        when(ratesCache.getMetrics()).thenReturn(new RatesCacheMetricsDTO(10,2));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/cache").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("cacheHits", is(10)).
                        body("cacheMisses", is(2));

        verify(ratesCache, times(1)).getMetrics();
    }


}
