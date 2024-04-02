package com.tqs108287.app.hw1_bustickets.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Locale;


@Entity
@Table(name = "City")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "site")
    private List<Stop> stops;

    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    private String district;

    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Include
    private Locale.IsoCountryCode countryCode;

    @Embedded
    @EqualsAndHashCode.Include
    private GeoLocation geoLocation;

}
