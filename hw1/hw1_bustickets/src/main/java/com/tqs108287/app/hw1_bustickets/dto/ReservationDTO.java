package com.tqs108287.app.hw1_bustickets.dto;

import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@AllArgsConstructor
@Getter
@Setter
public class ReservationDTO {

    static final Logger logger = getLogger(lookup().lookupClass());

    private Long tripId;
    private int seatNumber;
    private String clientName;
    private String clientAddress;

    public static ReservationDTO fromReservationEntity(Reservation reservation){
        return new ReservationDTO(reservation.getTrip().getId(), reservation.getSeatNumber(), reservation.getClientName(), reservation.getClientAddress());
    }

    public Reservation toReservationEntity(){
        Trip trip = new Trip();
        trip.setId(tripId);
        // TODO ver se isto aqui está a funcionar quando fizer integration test
        return new Reservation(trip, getSeatNumber(), getClientName(), getClientAddress());
    }
}
