package com.tqs108287.app.hw1_bustickets.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stop")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String district;

    public Stop(String name, String district) {
        this.name = name;
        this.district = district;
    }
}
