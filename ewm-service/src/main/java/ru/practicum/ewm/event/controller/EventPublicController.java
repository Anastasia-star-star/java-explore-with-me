package ru.practicum.ewm.event.controller;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.SortSearch;
import ru.practicum.ewm.event.service.EventPublicService;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class EventPublicController {

    private final EventPublicService publicService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getAllEvents(
            @RequestParam(defaultValue = "") String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(defaultValue = "VIEWS") SortSearch sort,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
            HttpServletRequest request) {

        log.info("get all events");
        return ResponseEntity.ok().body(publicService.getAllEvents(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> getPublicEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("getEventBy id = {}", id);
        return ResponseEntity.ok(publicService.getPublicEventById(id, request));
    }

}