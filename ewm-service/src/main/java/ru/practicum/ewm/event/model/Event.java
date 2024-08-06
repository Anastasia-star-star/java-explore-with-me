package ru.practicum.ewm.event.model;

import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "events_id")
    private Long id;

    @NotNull
    @Column(name = "events_annotation")
    private String annotation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_category_id")
    private Category category;

    @Column(name = "events_confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "events_created_on")
    private LocalDateTime createdOn;

    @NotNull
    @Column(name = "events_description")
    private String description;

    @NotNull
    @Column(name = "events_event_date")
    private LocalDateTime eventDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_initiator_id")
    private User initiator;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_location_id")
    private Location location;

    @NotNull
    @Column(name = "events_paid")
    private Boolean paid;

    @NotNull
    @Column(name = "events_participant_limit")
    private Integer participantLimit;

    @Column(name = "events_published_on")
    private LocalDateTime publishedOn;

    @Column(name = "events_request_moderation")
    private Boolean requestModeration;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "events_state")
    private StateEvent state;

    @NotNull
    @Column(name = "events_title")
    @NotBlank
    private String title;

}