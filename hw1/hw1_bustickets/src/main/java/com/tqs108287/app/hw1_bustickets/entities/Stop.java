package com.tqs108287.app.hw1_bustickets.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Stop")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Stop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Site site;

}
