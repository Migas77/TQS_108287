package com.tqs108287.app.hw1_bustickets.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @JsonManagedReference
    private Trip trip;

    @NotNull
    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @NotNull
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @NotNull
    @Column(name = "client_address", nullable = false)
    private String clientAddress;

    public Reservation(Trip trip, int seatNumber, String clientName, String clientAddress) {
        this.trip = trip;
        this.seatNumber = seatNumber;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
    }
}