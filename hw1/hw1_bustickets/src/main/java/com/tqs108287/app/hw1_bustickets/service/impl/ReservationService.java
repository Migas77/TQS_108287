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
        logger.info("makeReservation(); arguments: reservationDTO={}", reservationDTO);

        Optional<Trip> tripOpt = tripService.getTripById(reservationDTO.getTripId());
        if (tripOpt.isEmpty()){
            logger.info("Trip with id {} not found", reservationDTO.getTripId());
            return Optional.empty();
        }

        Trip trip = tripOpt.get();
        int seatNumber = reservationDTO.getSeatNumber();

        if (seatNumberIsNotValid(trip, seatNumber)){
            logger.info("Seat Number {} not valid for trip with id {}", seatNumber, trip.getId());
            return Optional.empty();
        }

        if (busIsFull(trip)){
            logger.info("Bus for trip with id {} is full", trip.getId());
            return Optional.empty();
        }

        Optional<Reservation> reservationSameSeatOpt = reservationRepository.findByTripAndSeatNumber(trip, seatNumber);
        if (reservationSameSeatOpt.isPresent()){
            logger.info("There already exists a reservation for seat number {} of trip with id {}",seatNumber, trip.getId());
            return Optional.empty();
        }

        return Optional.of(reservationRepository.save(reservationDTO.toReservationEntity()));
    }

    @Override
    public Optional<Reservation> getReservationById(UUID id) {
        logger.info("getReservationById(): arguments: id={}", id);
        return reservationRepository.findById(id);
    }

    private boolean busIsFull(Trip trip){
        return tripService.getNumberReservationsByTrip(trip) == trip.getNumberOfSeats();
    }

    private boolean seatNumberIsNotValid(Trip trip, int seatNumber){
        return seatNumber < 1 || seatNumber > trip.getNumberOfSeats();
    }


}
