package com.tqs108287.app.hw1_bustickets.entities;

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
    private Trip trip;

    @NotNull
    private int seatNumber;

    @NotNull
    private String clientName;

    @NotNull
    private String clientAddress;

    public Reservation(Trip trip, int seatNumber, String clientName, String clientAddress) {
        this.trip = trip;
        this.seatNumber = seatNumber;
        this.clientName = clientName;
        this.clientAddress = clientAddress;
    }
}