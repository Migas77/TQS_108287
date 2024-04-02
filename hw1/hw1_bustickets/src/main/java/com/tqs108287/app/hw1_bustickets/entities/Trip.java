package com.tqs108287.app.hw1_bustickets.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Table(name = "Trip")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Route route;

    @OneToMany(mappedBy = "trip")
    private Set<Reservation> reservations;

    @NotNull
    private Float priceEuros;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private Integer numberOfSeats;

}
