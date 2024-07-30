package ru.practicum.ewm.controller;

import dto.EndpointHit;
import dto.ViewStats;
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
    public ResponseEntity<EndpointHit> saveEndpHit(@RequestBody EndpointHit endpointHitDto) {
        endpointHitDto = statsServerService.saveEndpHit(endpointHitDto);
        log.info("Add new info");
        return ResponseEntity.status(HttpStatus.CREATED).body(endpointHitDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getAllStats(
            @RequestParam @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = YYYY_MM_DD_HH_MM_SS) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Getting stats ");
        return ResponseEntity.ok().body(statsServerService.getAllStats(start, end, uris, unique));
    }

}