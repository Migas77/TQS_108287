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

    @ManyToOne
    @JoinColumn(name = "origin_stop_id")
    private Stop originStop;

    @ManyToOne
    @JoinColumn(name = "destination_stop_id")
    private Stop destinationStop;

    @NotNull
    @Column(name = "leg_time")
    private LocalTime legTime;

    public Leg(Stop originStop, Stop destinationStop, LocalTime legTime) {
        this.originStop = originStop;
        this.destinationStop = destinationStop;
        this.legTime = legTime;
    }
}
