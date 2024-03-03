package com.tqs108287.app.lab3_2cars;

import com.tqs108287.app.lab3_2cars.boundary.CarRestController;
import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.service.CarManagerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarRestController.class)
public class CarRestController_WithMockServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car porshe = new Car("porshe", "911 turbo s");

        when( service.save(Mockito.any()) ).thenReturn(porshe);

        mvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(porshe)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("porshe")));

        verify(service, times(1)).save(Mockito.any());

    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception{
        Car porshe = new Car("porshe", "911 turbo s");
        Car opel = new Car("opel", "corsa");
        Car volkswagen = new Car("volkswagen", "golf");
        List<Car> allCars = Arrays.asList(porshe, opel, volkswagen);
        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].model", is(porshe.getModel())))
                .andExpect(jsonPath("$[1].model", is(opel.getModel())))
                .andExpect(jsonPath("$[2].model", is(volkswagen.getModel())));

        verify(service, times(1)).getAllCars();

    }


    @Test
    void whenGetCar_thenReturnCar() throws Exception{
        Car porshe = new Car(1L,"porshe", "911 turbo s");
        when(service.getCarDetails(porshe.getCarId())).thenReturn(Optional.of(porshe));

        mvc.perform(
                get("/api/car/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model", is(porshe.getModel())));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }
}
