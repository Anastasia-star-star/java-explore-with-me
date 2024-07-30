package ru.practicum.ewm.compilation.model;

import ru.practicum.ewm.event.model.Event;

import java.util.Set;
import javax.persistence.*;

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
    private Long id; // Идентификатор

    @ManyToMany
    @JoinTable(
            name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events; // Список идентификаторов событий, входящих в подборку

    @Column(name = "compilations_pinned", nullable = false)
    private Boolean pinned; // Закреплена ли подборка на главной странице сайта

    @Column(name = "compilations_title", nullable = false)
    private String title; // Заголовок подборки

}