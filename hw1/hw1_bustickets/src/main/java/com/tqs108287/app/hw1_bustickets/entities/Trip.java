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
@AllArgsConstructor
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

    @NotNull
    @Column(name = "number_of_seats", nullable = false)
    private int numberOfSeats;

    @NotNull
    @Column(name = "price_euros", nullable = false)
    private float priceEuros;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    public Trip(Route route, Set<Reservation> reservations, int numberOfSeats, float priceEuros, LocalDateTime departureTime) {
        this.route = route;
        this.reservations = reservations;
        this.numberOfSeats = numberOfSeats;
        this.priceEuros = priceEuros;
        this.departureTime = departureTime;
    }
}
