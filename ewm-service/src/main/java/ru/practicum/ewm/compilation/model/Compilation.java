package ru.practicum.ewm.compilation.model;

import ru.practicum.ewm.event.model.Event;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "compilations")
@NoArgsConstructor
@Getter
@Setter
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilations_id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;

    @NotNull
    @Column(name = "compilations_pinned")
    private Boolean pinned;

    @NotNull
    @Column(name = "compilations_title")
    private String title;

}