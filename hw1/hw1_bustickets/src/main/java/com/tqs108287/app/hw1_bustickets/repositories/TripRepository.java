package com.tqs108287.app.hw1_bustickets.repositories;


import com.tqs108287.app.hw1_bustickets.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT t FROM Trip t " +
            "JOIN t.route rota " +
            "JOIN rota.legs origin " +
            "JOIN rota.legs destination " +
            "WHERE origin.originStop.id = :origin_id AND destination.destinationStop.id = :destination_id " +
            "AND DATE(t.departureTime) = :departure_date " +
            "AND INDEX(origin) <= INDEX(destination) ")
    List<Trip> findTripsBetweenStopsOnDate(@Param("origin_id") long originId,
                                           @Param("destination_id") long destinationId,
                                           @Param("departure_date") LocalDate departureDate);

    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.trip = :trip")
    long countReservationsByTrip(@Param("trip") Trip trip);

}
