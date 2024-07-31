package ru.practicum.ewm.request.model;

import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participation_requests")
@NoArgsConstructor
@Getter
@Setter
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_requests_id")
    private Long id;

    @NotNull
    @Column(name = "participation_requests_created")
    private LocalDateTime created;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_requests_event_id")
    private Event event;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_requests_requester_id")
    private User requester;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "participation_requests_status")
    private StateRequest status;

}