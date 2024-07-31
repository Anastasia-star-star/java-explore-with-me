package ru.practicum.ewm.event.controller;

import ru.practicum.ewm.event.dto.creating.NewEventDto;
import ru.practicum.ewm.event.dto.info.EventFullDto;
import ru.practicum.ewm.event.dto.info.EventShortDto;
import ru.practicum.ewm.event.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.event.dto.update.UpdateEventUserRequest;
import ru.practicum.ewm.event.service.EventPrivateService;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPrivateController {

    private final EventPrivateService privateService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllEventsByUser(@PathVariable Long userId,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("get All Events");
        return ResponseEntity.ok().body(privateService.getAllEventsByUser(userId, from, size));
    }

    @PostMapping
    @Validated
    public ResponseEntity<EventFullDto> saveEvent(@PathVariable Long userId,
                                                  @Valid @RequestBody NewEventDto newEventDto) {
        log.info("save event");
        return ResponseEntity.status(HttpStatus.CREATED).body(privateService.saveEvent(userId, newEventDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        log.info("getting new event");
        return ResponseEntity.ok(privateService.getEventById(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(@PathVariable Long userId, @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("update Event");
        return ResponseEntity.ok(privateService.updateEvent(userId, eventId, updateEventUserRequest));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getAllRequestsOfEventByUser(@PathVariable Long userId,
                                                                                     @PathVariable Long eventId) {
        log.info("get all requests of Event");
        return ResponseEntity.ok().body(privateService.getAllRequestsOfEventByUser(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateAllRequestsOfEventByUser(@PathVariable Long userId,
                                                                                         @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("update all requests of event");
        return ResponseEntity.ok(privateService.updateAllRequestsOfEventByUser(userId,
                eventId, eventRequestStatusUpdateRequest));
    }

}