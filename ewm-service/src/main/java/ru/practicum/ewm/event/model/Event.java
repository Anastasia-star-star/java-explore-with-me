package ru.practicum.ewm.event.model;

import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import javax.persistence.*;

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

    @Column(name = "events_annotation", nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_category_id", nullable = false)
    private Category category;

    @Column(name = "events_confirmed_requests")
    private Long confirmedRequests;

    @Column(name = "events_created_on")
    private LocalDateTime createdOn;

    @Column(name = "events_description", nullable = false)
    private String description;

    @Column(name = "events_event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_initiator_id", nullable = false)
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_location_id", nullable = false)
    private Location location;

    @Column(name = "events_paid", nullable = false)
    private Boolean paid;

    @Column(name = "events_participant_limit", nullable = false)
    private Integer participantLimit;

    @Column(name = "events_published_on")
    private LocalDateTime publishedOn;

    @Column(name = "events_request_moderation")
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "events_state", nullable = false)
    private StateEvent state;

    @Column(name = "events_title", nullable = false)
    private String title;

}