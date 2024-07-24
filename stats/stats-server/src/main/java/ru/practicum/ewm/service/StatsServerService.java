package ru.practicum.ewm.service;

import dto.EndpointHit;
import dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsServerService {

    EndpointHit saveEndpHit(EndpointHit endpointHit);

    List<ViewStats> getAllStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}