package ru.practicum.ewm.event.controller;

import ru.practicum.ewm.event.dto.info.EventFullDto;
import ru.practicum.ewm.event.dto.update.UpdateEventAdminRequest;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.service.EventAdminService;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventAdminController {

    private final EventAdminService adminService;

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getAllEventsByAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<StateEvent> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime rangeEnd,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("get all events by Admin");
        return ResponseEntity.ok().body(adminService.getAllEventsByAdmin(
                users, states, categories, rangeStart, rangeEnd, from, size));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventByAdmin(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("update event by Admin by id = {}", eventId);
        return ResponseEntity.ok(adminService.updateEventByAdmin(eventId, updateEventAdminRequest));
    }

}