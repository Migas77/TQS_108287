package com.tqs108287.app.hw1_bustickets.service.impl;

import com.tqs108287.app.hw1_bustickets.dto.RatesDTO;
import com.tqs108287.app.hw1_bustickets.dto.TripDetailsDTO;
import com.tqs108287.app.hw1_bustickets.entities.Trip;
import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import com.tqs108287.app.hw1_bustickets.service.ITripService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class TripService implements ITripService {

    static final Logger logger = getLogger(lookup().lookupClass());

    private TripRepository tripRepository;
    private RatesService ratesService;


    @Override
    public List<TripDetailsDTO> getAllTripsDetailsOnDate(long originId, long destinationId, String currency, LocalDate date) {
        List<Trip> foundTrips = tripRepository.findTripsBetweenStopsOnDate(originId, destinationId, date);
        if (foundTrips.isEmpty()){
            logger.info("didn't found any trips");
            return List.of();
        }
        if (currency.equals("EUR")){
            return foundTrips.stream().map(TripDetailsDTO::fromTripEntity).toList();
        }
        RatesDTO ratesDTO = ratesService.getRatesFromEurTo(currency)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency Not Found"));
        return foundTrips.stream()
                .map(trip -> {
                    TripDetailsDTO tripDetailsDTO = TripDetailsDTO.fromTripEntity(trip);
                    tripDetailsDTO.setCurrency(ratesDTO.getCurrency());
                    tripDetailsDTO.setPrice(tripDetailsDTO.getPrice() * ratesDTO.getRate());
                    return tripDetailsDTO;
                }).toList();
    }

    @Override
    public Optional<Trip> getTripById(long tripId) {
        return tripRepository.findById(tripId);
    }

    @Override
    public Optional<TripDetailsDTO> getTripDetailsById(long tripId) {
        return tripRepository.findById(tripId).map(TripDetailsDTO::fromTripEntity);
    }

    @Override
    public long getNumberReservationsByTrip(Trip trip) {
        return tripRepository.countReservationsByTrip(trip);
    }


}
