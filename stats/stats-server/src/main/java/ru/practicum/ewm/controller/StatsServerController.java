package ru.practicum.ewm.controller;

import ru.practicum.ewm.dto.EndpHit;
import ru.practicum.ewm.dto.ViewStats;
import ru.practicum.ewm.service.StatsServerService;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsServerController {

    private final StatsServerService statsServerService;

    @PostMapping("/hit")
    public ResponseEntity<EndpHit> saveHit(@RequestBody EndpHit endpHitDto) {
        endpHitDto = statsServerService.saveEndpointHit(endpHitDto);
        log.info("Add new info about request: {}", endpHitDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(endpHitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getAllStats(
            @RequestParam @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        List<ViewStats> stats = statsServerService.getAllStats(start, end, uris, unique);
        log.info("Getting new info start = {}, end = {}, uris = {}, unique = {}.",
                start, end, uris, unique);
        return ResponseEntity.ok().body(stats);
    }

}