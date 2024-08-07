package ru.practicum.ewm.service;

import dto.EndpointHit;
import dto.ViewStats;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.HitDontSaveException;
import ru.practicum.ewm.mapper.StatsServerMapper;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.repository.StatsServerRepository;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatsServerServiceImpl implements StatsServerService {

    private final StatsServerRepository statsServerRepository;

    @Transactional
    @Override
    public EndpointHit saveEndpHit(EndpointHit endpointHit) {
        try {
            Hit hit = statsServerRepository.save(StatsServerMapper.INSTANCE.toHit(endpointHit));
            return StatsServerMapper.INSTANCE.toEndpointHit(hit);
        } catch (DataIntegrityViolationException e) {
            throw new HitDontSaveException("Информация о запросе не была сохранена: " + endpointHit);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<ViewStats> getAllStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (end.isBefore(start)) {
            throw new BadRequestException("Date must be before time start");
        }

        boolean hasUris = uris != null;

        if (unique) {
            return hasUris ?
                    statsServerRepository.getAllUniqueStatsWithUris(start, end, uris) :
                    statsServerRepository.getAllUniqueStats(start, end);
        } else {
            return hasUris ?
                    statsServerRepository.getAllStatsWithUris(start, end, uris) :
                    statsServerRepository.getAllStats(start, end);
        }
    }
}
