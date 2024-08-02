package ru.practicum.ewm.request.controller;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.service.RequestPrivateService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
public class RequestPrivateController {

    private final RequestPrivateService privateService;

    @GetMapping
    public List<ParticipationRequestDto> getAllRequestsByUser(@PathVariable Long userId) {
        log.info("Getting all requests by userId {}", userId);
        return privateService.getAllRequestsByUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto saveRequest(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Save your request to participate in the event");
        return privateService.saveRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto updateRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Cancelled your request to participate in the event");
        return privateService.updateRequest(userId, requestId);
    }

}