package ru.practicum.ewm.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "hits", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hits_id")
    private Long id;

    @NotNull
    @Column(name = "hits_app")
    private String app;

    @NotNull
    @Column(name = "hits_uri")
    private String uri;

    @NotNull
    @Column(name = "hits_ip")
    private String ip;

    @NotNull
    @Column(name = "hits_timestamp")
    private LocalDateTime timestamp;

}