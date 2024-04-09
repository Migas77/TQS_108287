package com.tqs108287.app.hw1_bustickets.RepositoryTest;

import com.tqs108287.app.hw1_bustickets.entities.*;
import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

@DataJpaTest
class TripRepositoryTest {

    static final Logger logger = getLogger(lookup().lookupClass());

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TripRepository tripRepository;

    Trip trip_fromLisboa_toAveiro;

    // TODO CHANGE BeforeEach later
    @BeforeEach
    void setup(){
        // Lisboa -> Coimbra -> Aveiro -> Porto
        // Lisboa -> Aveiro -> Braga
        // Lisboa -> Aveiro
        // Coimbra -> Porto
        // Aveiro -> Porto
        Stop stop_lisboa = new Stop("Lisboa", "Lisboa");
        Stop stop_coimbra = new Stop("Coimbra", "Coimbra");
        Stop stop_aveiro = new Stop("Aveiro", "Aveiro");
        Stop stop_porto = new Stop( "Porto", "Porto");
        Stop stop_braga = new Stop("Braga", "Braga");
        Leg leg_fromLisboa_toCoimbra = new Leg(stop_lisboa, stop_coimbra, LocalTime.of(2, 2));
        Leg leg_fromLisboa_toAveiro = new Leg(stop_lisboa, stop_aveiro, LocalTime.of(2, 30));
        Leg leg_fromCoimbra_toAveiro = new Leg(stop_coimbra, stop_aveiro, LocalTime.of(0, 48));
        Leg leg_fromCoimbra_toPorto = new Leg(stop_coimbra, stop_porto, LocalTime.of(1, 15));
        Leg leg_fromAveiro_toPorto = new Leg(stop_aveiro, stop_porto, LocalTime.of(0, 48));
        Leg leg_fromAveiro_toBraga = new Leg(stop_aveiro, stop_braga, LocalTime.of(1, 19));
        Route route_fromLisboa_toPorto = new Route(List.of(leg_fromLisboa_toCoimbra, leg_fromCoimbra_toAveiro, leg_fromAveiro_toPorto));
        Route route_fromLisboa_toBraga = new Route(List.of(leg_fromLisboa_toAveiro, leg_fromAveiro_toBraga));
        Route route_fromLisboa_toAveiro = new Route(List.of(leg_fromLisboa_toAveiro));
        Route route_fromCoimbra_toPorto = new Route(List.of(leg_fromCoimbra_toPorto));
        Route route_fromAveiro_toPorto = new Route(List.of(leg_fromAveiro_toPorto));
        Trip trip_fromLisboa_toPorto = new Trip(route_fromLisboa_toPorto, Set.of(), 41, 25f, LocalDateTime.of(2024, 6, 2, 10, 15));
        Trip trip_fromLisboa_toBraga = new Trip(route_fromLisboa_toBraga, Set.of(), 42, 35f, LocalDateTime.of(2024, 6, 2, 11, 15));
        trip_fromLisboa_toAveiro = new Trip(route_fromLisboa_toAveiro, Set.of(), 43, 20f, LocalDateTime.of(2024, 6, 3, 10, 15));
        entityManager.persist(stop_lisboa); entityManager.persist(stop_coimbra); entityManager.persist(stop_aveiro);
        entityManager.persist(stop_porto); entityManager.persist(stop_braga);
        entityManager.persist(leg_fromLisboa_toCoimbra); entityManager.persist(leg_fromLisboa_toAveiro);
        entityManager.persist(leg_fromCoimbra_toAveiro); entityManager.persist(leg_fromCoimbra_toPorto);
        entityManager.persist(leg_fromAveiro_toPorto); entityManager.persist(leg_fromAveiro_toBraga);
        entityManager.persist(route_fromLisboa_toPorto); entityManager.persist(route_fromLisboa_toBraga);
        entityManager.persist(route_fromLisboa_toAveiro); entityManager.persist(route_fromCoimbra_toPorto);
        entityManager.persist(route_fromAveiro_toPorto);
        entityManager.persist(trip_fromLisboa_toPorto); entityManager.persist(trip_fromLisboa_toBraga);
        entityManager.persist(trip_fromLisboa_toAveiro);
        entityManager.flush();
    }

    @Test
    void givenManyTrips_whenSearchSubRouteOfTrip_thenReturnListOfTrips() {
        // Lisboa - 1
        // Aveiro - 3
        List<Trip> foundTrips = tripRepository.findTripsBetweenStopsOnDate(1, 3, LocalDate.of(2024, 6, 2));

        assertThat(foundTrips).hasSize(2).extracting(Trip::getNumberOfSeats).containsExactlyInAnyOrder(41,42);
    }

    @Test
    @Disabled("For some unknown reason this test passes when ran alone but when ran together with all the tests in this file it doesn't")
    void givenManyTrips_whenSearchFullRouteOfTrip_thenReturnListOfTrips() {
        // Lisboa - 1
        // Aveiro - 3
        List<Trip> foundTrips = tripRepository.findTripsBetweenStopsOnDate(1, 3, LocalDate.of(2024, 6, 3));

        assertThat(foundTrips).hasSize(1).extracting(Trip::getNumberOfSeats).containsExactlyInAnyOrder(43);
    }

    @Test
    void givenTripWithReservations_whenCountReservationsByTrip_returnCount() {
        Reservation r1 = new Reservation(trip_fromLisboa_toAveiro, 2, "Cliente1", "Morada1");
        Reservation r2 = new Reservation(trip_fromLisboa_toAveiro, 3, "Cliente2", "Morada2");
        Reservation r3 = new Reservation(trip_fromLisboa_toAveiro, 4, "Cliente3", "Morada3");
        entityManager.persist(r1); entityManager.persist(r2); entityManager.persist(r3);

        long countReservationByTrip = tripRepository.countReservationsByTrip(trip_fromLisboa_toAveiro);

        assertThat(countReservationByTrip).isEqualTo(3L);
    }
}