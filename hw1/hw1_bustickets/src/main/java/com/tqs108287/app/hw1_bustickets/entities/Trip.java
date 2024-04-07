package com.tqs108287.app.hw1_bustickets.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "trip")
@NoArgsConstructor
@Getter
@Setter
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    // Default fetch: Lazy
    @OneToMany(mappedBy = "trip")
    private Set<Reservation> reservations;

    @Formula("(SELECT count(*) FROM reservation WHERE reservation.trip_id = id)")
    @Setter(AccessLevel.NONE)
    private int reservationsCount;

    @NotNull
    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @NotNull
    @Column(name = "price_euros", nullable = false)
    private float priceEuros;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    public Trip(Long id, Route route, Set<Reservation> reservations, int numberOfSeats, float priceEuros, LocalDateTime departureTime) {
        this.id = id;
        this.route = route;
        this.reservations = reservations;
        this.numberOfSeats = numberOfSeats;
        this.priceEuros = priceEuros;
        this.departureTime = departureTime;
    }
}
