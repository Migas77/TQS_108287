package com.tqs108287.app.lab3_2cars;

import com.tqs108287.app.lab3_2cars.boundary.CarRestController;
import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.service.CarManagerService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@WebMvcTest(CarRestController.class)
public class CarRestController_WithMockServiceTest_WithRestAssured {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService service;

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car porshe = new Car("porshe", "911 turbo s");

        when( service.save(Mockito.any()) ).thenReturn(porshe);

        RestAssuredMockMvc.
                given().
                        mockMvc(mockMvc).
                        contentType(ContentType.JSON).
                        body(porshe).
                when().
                        post("/api/cars").
                then().
                        statusCode(HttpStatus.SC_CREATED).
                        body("maker", is("porshe"));

        verify(service, times(1)).save(Mockito.any());

    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception{
        Car porshe = new Car("porshe", "911 turbo s");
        Car opel = new Car("opel", "corsa");
        Car volkswagen = new Car("volkswagen", "golf");
        List<Car> allCars = Arrays.asList(porshe, opel, volkswagen);
        when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc.
                given().
                    mockMvc(mockMvc).
                when().
                    get("/api/cars").
                then().
                    statusCode(HttpStatus.SC_OK).
                    body("size()", equalTo(3)).
                    body("model", Matchers.contains(porshe.getModel(), opel.getModel(), volkswagen.getModel()));

        // contains enforces order

        verify(service, times(1)).getAllCars();

    }

    @Test
    void givenManyCars_whenGetCarsByMaker_thenReturnJsonArray() throws Exception{
        Car porshe0 = new Car("porshe", "911 turbo s");
        Car porshe1 = new Car("porshe", "gt3rs");
        Car volkswagen = new Car("volkswagen", "golf");
        List<Car> porshes = Arrays.asList(porshe0, porshe1);
        when(service.getCarsByMaker("porshe")).thenReturn(porshes);

        RestAssuredMockMvc.
                given().
                    mockMvc(mockMvc).
                when().
                    get("/api/cars?maker=porshe").
                then().
                    statusCode(HttpStatus.SC_OK).
                    body("size()", equalTo(2)).
                    body("model", Matchers.contains(porshe0.getModel(), porshe1.getModel()));

        verify(service, times(1)).getCarsByMaker("porshe");

    }


    @Test
    void whenGetCar_thenReturnCar() throws Exception{
        Car porshe = new Car(1L,"porshe", "911 turbo s");
        when(service.getCarDetails(porshe.getCarId())).thenReturn(Optional.of(porshe));

        RestAssuredMockMvc.
                given().
                    mockMvc(mockMvc).
                when().
                    get("/api/car/1").
                then().
                    statusCode(HttpStatus.SC_OK).
                    body("model", is(porshe.getModel()));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }
}
