package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EndpHit;
import ru.practicum.ewm.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerService {

    EndpHit saveEndpointHit(EndpHit endpHit);

    List<ViewStats> getAllStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}