package ru.practicum.ewm.util;

import dto.ViewStats;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;

import ru.practicum.ewm.event.dto.LocationDto;
import ru.practicum.ewm.event.mapper.LocationMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.Location;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.repository.LocationRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static constant.Constants.FORMATTER_FOR_DATETIME;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UtilService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final CompilationRepository compilationRepository;
    private final Statistic statistic;

    public User returnUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    public Category returnCategory(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с id = " + catId + " не найден."));
    }

    public Event returnEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Событие с идентификатором " + eventId + " не найдено."));
    }

    public void checkEventInitiator(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotFoundException("Пользователь с id = " + userId +
                    " не является инициатором события с id = " + event.getId());
        }
    }

    public ParticipationRequest returnRequest(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Запрос на участие с идентификатором " + requestId + " не найден."));
    }

    @Transactional
    public Location returnLocation(LocationDto locationDto) {
        Location location = locationRepository.findByLatAndLon(locationDto.getLat(), locationDto.getLon());
        return location != null ? location : locationRepository.save(LocationMapper.INSTANCE.toLocation(locationDto));
    }

    public Compilation returnCompilation(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException("Подборка событий с идентификатором " + compId + " не найдена."));
    }

    public Map<Long, Long> returnMapViewStats(List<Event> events, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        List<String> uris = events.stream()
                .map(event -> "/events/" + event.getId())
                .collect(Collectors.toList());
        List<ViewStats> viewStatList = statistic.statsClient.getAllStats(rangeStart.format(FORMATTER_FOR_DATETIME),
                rangeEnd.format(FORMATTER_FOR_DATETIME), uris, true);

        Map<Long, Long> views = new HashMap<>();
        for (ViewStats viewStats : viewStatList) {
            Long id = Long.parseLong(viewStats.getUri().split("/events/")[1]);
            views.put(id, views.getOrDefault(id, 0L) + 1);
        }
        return views;
    }

}