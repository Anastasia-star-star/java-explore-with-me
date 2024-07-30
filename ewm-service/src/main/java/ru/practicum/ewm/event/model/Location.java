package ru.practicum.ewm.event.model;

import javax.persistence.*;

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

    @Column(name = "locations_lat", nullable = false)
    private Float lat;

    @Column(name = "locations_lon", nullable = false)
    private Float lon;

}