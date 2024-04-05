package com.tqs108287.app.hw1_bustickets.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "route")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @OrderColumn(name = "legs_index")
    private List<Leg> legs;

    public Route(List<Leg> legs) {
        this.legs = legs;
    }
}
