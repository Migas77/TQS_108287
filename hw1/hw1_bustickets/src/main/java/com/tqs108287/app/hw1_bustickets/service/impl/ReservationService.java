package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.dto.ReservationDTO;
import com.tqs108287.app.hw1_bustickets.entities.Reservation;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.repositories.ReservationRepository;
import com.tqs108287.app.hw1_bustickets.service.IReservationService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ReservationService implements IReservationService {

    static final Logger logger = getLogger(lookup().lookupClass());

    ReservationRepository reservationRepository;
    TripService tripService;

    @Override
    public Optional<Reservation> makeReservation(ReservationDTO reservationDTO) {
        logger.info("calling make reservation");

        Optional<Trip> tripOpt = tripService.getTripById(reservationDTO.getTripId());
        if (tripOpt.isEmpty()){
            logger.info("trip with this id doesn't exist");
            return Optional.empty();
        }

        logger.info("trip with this id does exist");

        Trip trip = tripOpt.get();
        int seatNumber = reservationDTO.getSeatNumber();

        if (seatNumberIsNotValid(trip, seatNumber)){
            logger.info("seat number not valid");
            return Optional.empty();
        }

        logger.info("seat number is valid");

        if (busIsFull(trip)){
            logger.info("bus is full");
            return Optional.empty();
        }

        logger.info("bus is not full");

        Optional<Reservation> reservationSameSeatOpt = reservationRepository.findByTripAndSeatNumber(trip, seatNumber);
        logger.info(String.valueOf(reservationSameSeatOpt.isPresent()));
        if (reservationSameSeatOpt.isPresent()){
            logger.info("it exists a reservation for this seat");
            return Optional.empty();
        }

        logger.info("creating");
        Reservation toSave = reservationDTO.toReservationEntity(trip);

        logger.info("returning");

        return Optional.of(reservationRepository.save(toSave));
    }

    @Override
    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    private boolean busIsFull(Trip trip){
        return tripService.getNumberReservationsByTrip(trip) == trip.getNumberOfSeats();
    }

    private boolean seatNumberIsNotValid(Trip trip, int seatNumber){
        return seatNumber < 1 || seatNumber > trip.getNumberOfSeats();
    }


}
