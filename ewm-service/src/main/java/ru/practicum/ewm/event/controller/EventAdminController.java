package ru.practicum.ewm.event.controller;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
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
public class EventAdminController {

    private final EventAdminService adminService;

    @GetMapping
    /**
     * Получение событий.
     */
    public ResponseEntity<List<EventFullDto>> getAllEventsByAdmin(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<StateEvent> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime rangeEnd,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        List<EventFullDto> eventDtos = adminService.getAllEventsByAdmin(
                users, states, categories, rangeStart, rangeEnd, from, size);
        log.info("Получен список событий, users = {}, states = {}, categories = {}, rangeStart = {}, rangeEnd = {}, " +
                "from = {}, size = {}.", users, states, categories, rangeStart, rangeEnd, from, size);
        return ResponseEntity.ok().body(eventDtos);
    }

    @PatchMapping("/{eventId}")
    @Validated
    /**
     * Редактирование данных события и его статуса (отклонение/публикация).
     */
    public ResponseEntity<EventFullDto> updateEventByAdmin(
            @PathVariable Long eventId,
            @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        EventFullDto eventFullDto = adminService.updateEventByAdmin(eventId, updateEventAdminRequest);
        log.info("Обновлено событие админом, с id = {}: {}.", eventId, eventFullDto);
        return ResponseEntity.ok(eventFullDto);
    }

}