package ru.practicum.ewm.repository;

import dto.ViewStats;
import ru.practicum.ewm.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatsServerRepository extends JpaRepository<Hit, Long> {

    @Query("select new dto.ViewStats(h.app, h.uri, COUNT(h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between ?1 and ?2 " +
            "and h.uri in ?3 " +
            "group by h.app, h.uri " +
            "order by COUNT(h.ip) desc")
    List<ViewStats> getAllStatsWithUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new dto.ViewStats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between ?1 and ?2 " +
            "group by h.app, h.uri " +
            "order by COUNT(DISTINCT h.ip) desc")
    List<ViewStats> getAllUniqueStats(LocalDateTime start, LocalDateTime end);

    @Query("select new dto.ViewStats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between ?1 and ?2 " +
            "and h.uri in ?3 " +
            "group by h.app, h.uri " +
            "order by COUNT(DISTINCT h.ip) desc")
    List<ViewStats> getAllUniqueStatsWithUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new dto.ViewStats(h.app, h.uri, COUNT(h.ip)) " +
            "from Hit as h " +
            "where h.timestamp between ?1 and ?2 " +
            "group by h.app, h.uri " +
            "order by COUNT(h.ip) desc")
    List<ViewStats> getAllStats(LocalDateTime start, LocalDateTime end);
}
