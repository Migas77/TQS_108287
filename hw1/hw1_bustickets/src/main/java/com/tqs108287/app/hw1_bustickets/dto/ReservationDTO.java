package com.tqs108287.app.hw1_bustickets.dto;

import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {
    private Long tripId;
    private Integer seatNumber;
    private String clientName;
    private String clientAddress;

    public static ReservationDTO fromReservationEntity(Reservation reservation){
        return new ReservationDTO(reservation.getTrip().getId(), reservation.getSeatNumber(), reservation.getClientName(), reservation.getClientAddress());
    }

    public Reservation toReservationEntity(){
        Trip trip = new Trip();
        trip.setId(tripId); // this will definitely be a problem
        return new Reservation(trip, getSeatNumber(), getClientName(), getClientAddress());
    }
}
