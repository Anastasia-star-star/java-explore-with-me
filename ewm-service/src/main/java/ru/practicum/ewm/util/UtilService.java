package ru.practicum.ewm.util;

import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import dto.ViewStats;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.mapper.LocationMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.location.repository.LocationRepository;
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
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public Category returnCategory(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }

    public Event returnEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Event not found"));
    }

    public void checkEventInitiator(Event event, Long userId) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotFoundException("User is not initiator");
        }
    }

    public ParticipationRequest returnRequest(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Request not found"));
    }

    @Transactional
    public Location returnLocation(LocationDto locationDto) {
        locationDto.setRadius(0f);
        Location location = locationRepository
                .findByLatAndLonAndRadius(locationDto.getLat(), locationDto.getLon(), 0f);
        return location != null ? location
                : locationRepository.save(LocationMapper.INSTANCE.toLocation(locationDto));
    }

    public Location returnLocationById(Long locId) {
        return locationRepository.findById(locId).orElseThrow(() ->
                new NotFoundException("Location not found"));
    }

    public Compilation returnCompilation(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() ->
                new NotFoundException("Compilation not found"));
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