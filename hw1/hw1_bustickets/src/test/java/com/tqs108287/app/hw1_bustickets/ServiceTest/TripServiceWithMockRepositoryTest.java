package com.tqs108287.app.hw1_bustickets.ServiceTest;

import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.*;
import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import com.tqs108287.app.hw1_bustickets.service.impl.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripServiceWithMockRepositoryTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    Trip trip_fromLisboa_toPorto;
    Trip trip_fromLisboa_toBraga;

    // TODO clean this
    @BeforeEach
    void setup(){
        // Lisboa -> Coimbra -> Aveiro -> Porto
        // Lisboa -> Aveiro -> Braga
        // Lisboa -> Aveiro
        // Coimbra -> Porto
        // Aveiro -> Porto

        Stop stop_lisboa = new Stop(1L, "Lisboa", "Lisboa");
        Stop stop_coimbra = new Stop(2L, "Coimbra", "Coimbra");
        Stop stop_aveiro = new Stop(3L, "Aveiro", "Aveiro");
        Stop stop_porto = new Stop(4L, "Porto", "Porto");
        Stop stop_braga = new Stop(5L, "Braga", "Braga");
        Leg leg_fromLisboa_toCoimbra = new Leg(1L, stop_lisboa, stop_coimbra, LocalTime.of(2, 2));
        Leg leg_fromLisboa_toAveiro = new Leg(2L, stop_lisboa, stop_aveiro, LocalTime.of(2, 30));
        Leg leg_fromCoimbra_toAveiro = new Leg(3L, stop_coimbra, stop_aveiro, LocalTime.of(0, 48));
        Leg leg_fromAveiro_toPorto = new Leg(5L, stop_aveiro, stop_porto, LocalTime.of(0, 48));
        Leg leg_fromAveiro_toBraga = new Leg(6L, stop_aveiro, stop_braga, LocalTime.of(1, 19));
        Route route_fromLisboa_toPorto = new Route(1L, List.of(leg_fromLisboa_toCoimbra, leg_fromCoimbra_toAveiro, leg_fromAveiro_toPorto));
        Route route_fromLisboa_toBraga = new Route(2L, List.of(leg_fromLisboa_toAveiro, leg_fromAveiro_toBraga));
        trip_fromLisboa_toPorto = new Trip(1L, route_fromLisboa_toPorto, Set.of(),50, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        trip_fromLisboa_toBraga = new Trip(2L, route_fromLisboa_toBraga, Set.of(), 47, 35f, LocalDateTime.of(2024, 6, 2, 11, 15));
    }


    @Test
    void givenManyTrips_WhenSearchTripByInvalidId_ThenReturnEmptyOptional() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Trip> tripOpt = tripService.getTripById(123412);

        assertThat(tripOpt).isEmpty();
        verify(tripRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenManyTrips_WhenSearchTripByValidId_ThenReturnOptionalOfTrip(){
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto));

        Optional<Trip> tripOpt = tripService.getTripById(1);

        assertThat(tripOpt).isNotEmpty().hasValue(trip_fromLisboa_toPorto);
        verify(tripRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenManyTrips_WhenGetTripDetailsByInvalidId_ThenReturnEmptyOptional() {
        when(tripRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<TripDetailsDTO> tripOpt = tripService.getTripDetailsById(123412);

        assertThat(tripOpt).isEmpty();
        verify(tripRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenManyTrips_WhenGetTripDetailsByValidId_ThenReturnOptionalOfTrip(){
        when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip_fromLisboa_toPorto));//nope

        Optional<TripDetailsDTO> tripOpt = tripService.getTripDetailsById(1);

        assertThat(tripOpt)
                .isNotEmpty()
                .hasValueSatisfying(tripDetails -> assertThat(tripDetails.getId()).isEqualTo(trip_fromLisboa_toPorto.getId()));
        verify(tripRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenManyTrips_WhenSearchValidTripsFromOriginToDestInDate_ThenReturnListOfTrips() {
        when(tripRepository.findTripsBetweenStopsOnDate(anyLong(), anyLong(), any(LocalDate.class)))
                .thenReturn(List.of(trip_fromLisboa_toPorto, trip_fromLisboa_toBraga));

        List<TripDetailsDTO> trips = tripService.getAllTripsDetailsOnDate(1, 3, LocalDate.of(2024, 6, 2));

        assertThat(trips).hasSize(2).extracting(TripDetailsDTO::getId)
                .containsExactly(trip_fromLisboa_toPorto.getId(), trip_fromLisboa_toBraga.getId());
        verify(tripRepository, times(1)).findTripsBetweenStopsOnDate(anyLong(), anyLong(), any(LocalDate.class));
    }

    @Test
    void givenManyTrips_WhenSearchInvalidTripsFromOriginToDestInDate_ThenReturnListOfTrips() {
        when(tripRepository.findTripsBetweenStopsOnDate(anyLong(), anyLong(), any(LocalDate.class)))
                .thenReturn(List.of());

        List<TripDetailsDTO> trips = tripService.getAllTripsDetailsOnDate(1, 3, LocalDate.of(2024, 6, 2));

        assertThat(trips).isEmpty();
        verify(tripRepository, times(1)).findTripsBetweenStopsOnDate(anyLong(), anyLong(), any(LocalDate.class));
    }

}
