package ru.practicum.ewm.model;

import java.time.LocalDateTime;
import javax.persistence.*;

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

    @Column(name = "hits_app", nullable = false)
    private String app;

    @Column(name = "hits_uri", nullable = false)
    private String uri;

    @Column(name = "hits_ip", nullable = false)
    private String ip;

    @Column(name = "hits_timestamp", nullable = false)
    private LocalDateTime timestamp;

}