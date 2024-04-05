package com.tqs108287.app.hw1_bustickets.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalTime;

@Entity
@Table(name = "leg")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Leg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Stop originStop;

    @OneToOne
    private Stop destinationStop;

    @NotNull
    private LocalTime legTime;

    public Leg(Stop originStop, Stop destinationStop, LocalTime legTime) {
        this.originStop = originStop;
        this.destinationStop = destinationStop;
        this.legTime = legTime;
    }
}
