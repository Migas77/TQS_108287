package com.tqs108287.app.hw1_bustickets.ServiceTest;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.repositories.StopRepository;
import com.tqs108287.app.hw1_bustickets.service.impl.StopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StopServiceWithMockRepositoryTest {

    @Mock
    StopRepository stopRepository;

    @InjectMocks
    private StopService stopService;

    Stop stop_lisboa;
    Stop stop_coimbra;
    Stop stop_aveiro;


    // TODO clean this
    @BeforeEach
    void setup(){
        stop_lisboa = new Stop(1L, "Lisboa", "Lisboa");
        stop_coimbra = new Stop(2L, "Coimbra", "Coimbra");
        stop_aveiro = new Stop(3L, "Aveiro", "Aveiro");
    }

    @Test
    void givenManyStops_whenGetAll_thenReturnAll() {
        when(stopRepository.findAll()).thenReturn(List.of(stop_lisboa, stop_coimbra, stop_aveiro));

        List<Stop> stops = stopService.getAllStops();

        assertThat(stops).hasSize(3).
                extracting(Stop::getId).containsExactly(stop_lisboa.getId(), stop_coimbra.getId(), stop_aveiro.getId());
        verify(stopRepository, times(1)).findAll();
    }

    @Test
    void givenNoStops_whenGetAll_thenReturnEmptyList() {
        when(stopRepository.findAll()).thenReturn(List.of());

        List<Stop> stops = stopService.getAllStops();

        assertThat(stops).isEmpty();
        verify(stopRepository, times(1)).findAll();
    }

    @Test
    void givenManyStops_WhenGetStopByInvalidId_thenReturnEmptyOptional() {
        when(stopRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Stop> tripOpt = stopService.getStopById(123412);

        assertThat(tripOpt).isEmpty();
        verify(stopRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenManyTrips_WhenGetStopByValidId_thenReturnOptionalOfStop(){
        when(stopRepository.findById(anyLong())).thenReturn(Optional.of(stop_lisboa));//nope

        Optional<Stop> tripOpt = stopService.getStopById(1);

        assertThat(tripOpt)
                .isNotEmpty()
                .hasValueSatisfying(stop -> assertThat(stop.getId()).isEqualTo(stop_lisboa.getId()));
        verify(stopRepository, times(1)).findById(anyLong());
    }


}
