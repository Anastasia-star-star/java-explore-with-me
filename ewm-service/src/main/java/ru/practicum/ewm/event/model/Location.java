package ru.practicum.ewm.event.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Table(name = "locations", schema = "public")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locations_id")
    private Long id;

    @NotNull
    @Column(name = "locations_lat")
    private Float lat;

    @NotNull
    @Column(name = "locations_lon")
    private Float lon;

}