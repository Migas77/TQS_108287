package com.tqs108287.app.lab3_2cars;

import com.tqs108287.app.lab3_2cars.data.Car;
import com.tqs108287.app.lab3_2cars.data.CarRepository;
import com.tqs108287.app.lab3_2cars.service.CarManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarService_UnitTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp(){
        Car porshe0 = new Car(1L,"porshe", "911 turbo s");
        Car porshe1 = new Car("porshe", "gt3rs");
        Car opel = new Car("opel", "corsa");
        Car volkswagen = new Car("volkswagen", "golf");
        List<Car> porshes = Arrays.asList(porshe0, porshe1);
        List<Car> allCars = Arrays.asList(porshe0, porshe1, opel, volkswagen);

        when(carRepository.findByCarId(porshe0.getCarId())).thenReturn(porshe0);
        when(carRepository.findByCarId(opel.getCarId())).thenReturn(opel);
        when(carRepository.findByCarId(-111L)).thenReturn(null);
        when(carRepository.findAll()).thenReturn(allCars);
        when(carRepository.findByMaker("porshe")).thenReturn(porshes);
    }

    @Test
    void whenSearchValidId_thenCarShouldBeFound(){
        Long id = 1L;

        Optional<Car> found = carManagerService.getCarDetails(id);

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getCarId()).isEqualTo(id);
        verify(carRepository, times(1)).findByCarId(Mockito.anyLong());
    }

    @Test
    void whenSearchInvalidId_thenCarShouldNotBeFound(){
        Long id = -111L;

        Optional<Car> found = carManagerService.getCarDetails(id);

        assertThat(found.isPresent()).isFalse();
        verify(carRepository, times(1)).findByCarId(Mockito.anyLong());
    }

    @Test
    void given4Cars_whengetAll_thenReturn4Records() {
        List<Car> allCars = carManagerService.getAllCars();

        assertThat(allCars).hasSize(4).extracting(Car::getMaker).containsExactly("porshe", "porshe", "opel", "volkswagen");
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void given4Cars_whengetByMaker_thenReturnMakerRecords() {
        List<Car> allCars = carManagerService.getCarsByMaker("porshe");

        assertThat(allCars).hasSize(2).extracting(Car::getMaker).containsExactly("porshe", "porshe");
        verify(carRepository, times(1)).findByMaker("porshe");
    }




}
