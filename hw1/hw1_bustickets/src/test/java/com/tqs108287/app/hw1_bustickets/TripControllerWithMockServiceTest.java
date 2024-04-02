package com.tqs108287.app.hw1_bustickets;

import com.tqs108287.app.hw1_bustickets.boundary.TripRestController;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TripRestController.class)
public class TripControllerWithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ITripService service;

    @BeforeEach
    void setup(){
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    void someTest() {
        RestAssuredMockMvc.
        given().
        when().
                get("api/trips").
        then().
                statusCode(200);
    }

}
