package com.tqs108287.app.hw1_bustickets.dto;

import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Route;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Getter
@Setter
public class TripDetailsDTO {
    private Long id;
    private Route route;
    private List<Integer> availableSeats;
    private int numberOfSeats;
    private float price;
    private String currency;
    private LocalDateTime departureTime;

    public static TripDetailsDTO fromTripEntity(Trip trip){
        int numberOfSeats = trip.getNumberOfSeats();
        Set<Integer> reservedSeats = trip.getReservations().stream().map(Reservation::getSeatNumber).collect(Collectors.toSet());
        return new TripDetailsDTO(trip.getId(),
                trip.getRoute(),
                IntStream.rangeClosed(1, numberOfSeats).filter(seatNumber -> !reservedSeats.contains(seatNumber)).boxed().toList(),
                numberOfSeats,
                trip.getPriceEuros(),
                "EUR",
                trip.getDepartureTime());
    }
}
