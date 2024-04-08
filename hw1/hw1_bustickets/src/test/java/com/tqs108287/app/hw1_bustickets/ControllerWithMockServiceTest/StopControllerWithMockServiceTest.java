package com.tqs108287.app.hw1_bustickets.ControllerWithMockServiceTest;

import com.tqs108287.app.hw1_bustickets.boundary.StopRestController;
import com.tqs108287.app.hw1_bustickets.boundary.TripRestController;
import com.tqs108287.app.hw1_bustickets.entities.Leg;
import com.tqs108287.app.hw1_bustickets.entities.Route;
import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.slf4j.LoggerFactory.getLogger;

@WebMvcTest(StopRestController.class)
public class StopControllerWithMockServiceTest {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStopService service;

    Stop stop_lisboa;
    Stop stop_coimbra;
    Stop stop_aveiro;

    // TODO CHANGE BeforeEach later
    @BeforeEach
    void setup(){
        stop_lisboa = new Stop(15L, "Lisboa", "Lisboa");
        stop_coimbra = new Stop(22L, "Coimbra", "Coimbra");
        stop_aveiro = new Stop(3L, "Aveiro", "Aveiro");
    }

    @Test
    void givenManyStops_whenSearchAll_returnList() {
        when(service.getAllStops()).thenReturn(List.of(stop_lisboa, stop_coimbra, stop_aveiro));

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/stops").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("size()", is(3)).
                        body("id", Matchers.containsInAnyOrder(stop_lisboa.getId().intValue(), stop_coimbra.getId().intValue(), stop_aveiro.getId().intValue()));

        verify(service, times(1)).getAllStops();
    }

    @Test
    void givenNoStops_whenSearchAll_returnEmptyList() {
        when(service.getAllStops()).thenReturn(List.of());

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                when().
                        get("api/stops").
                then().
                        statusCode(HttpStatus.SC_OK).
                        body("size()", is(0));

        verify(service, times(1)).getAllStops();
    }
}
