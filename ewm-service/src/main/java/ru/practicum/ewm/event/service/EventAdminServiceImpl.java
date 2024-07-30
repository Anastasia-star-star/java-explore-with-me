package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.info.EventFullDto;
import ru.practicum.ewm.event.dto.update.UpdateEventAdminRequest;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotSaveException;
import ru.practicum.ewm.request.model.StateRequest;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.util.UtilService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UtilService utilService;

    @Transactional(readOnly = true)
    @Override
    public List<EventFullDto> getAllEventsByAdmin(
            List<Long> users, List<StateEvent> states, List<Long> categories,
            LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));

        if (rangeStart == null) {
            rangeStart = LocalDateTime.now();
        }

        if (rangeEnd == null) {
            rangeEnd = LocalDateTime.now().plusYears(100);
        }

        List<Event> events = eventRepository.getAllEventsByAdmin(users, states,
                categories, rangeStart, rangeEnd, page);
        Map<Long, Long> views = utilService.returnMapViewStats(events, rangeStart, rangeEnd);
        List<EventFullDto> eventFullDtos = EventMapper.INSTANCE.convertEventListToEventFullDtoList(events);

        eventFullDtos = eventFullDtos.stream()
                .peek(dto -> dto.setConfirmedRequests(
                        requestRepository.countByEventIdAndStatus(dto.getId(), StateRequest.CONFIRMED)))
                .peek(dto -> dto.setViews(views.getOrDefault(dto.getId(), 0L)))
                .collect(Collectors.toList());

        return eventFullDtos;
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = utilService.returnEvent(eventId);

        Optional.ofNullable(updateEventAdminRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updateEventAdminRequest.getCategory())
                .map(utilService::returnCategory)
                .ifPresent(event::setCategory);
        Optional.ofNullable(updateEventAdminRequest.getDescription()).ifPresent(event::setDescription);

        Optional.ofNullable(updateEventAdminRequest.getEventDate())
                .ifPresent(eventDate -> {
                    if (LocalDateTime.now().plusHours(1).isAfter(eventDate)) {
                        throw new BadRequestException(String.format(
                                "Дата начала изменяемого события должна быть не ранее, чем за час от даты публикации, eventId = %s, updateEventAdminRequest: %s.",
                                eventId, updateEventAdminRequest));
                    }
                    event.setEventDate(eventDate);
                });

        Optional.ofNullable(updateEventAdminRequest.getLocation())
                .map(utilService::returnLocation)
                .ifPresent(event::setLocation);

        Optional.ofNullable(updateEventAdminRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(updateEventAdminRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(updateEventAdminRequest.getRequestModeration()).ifPresent(event::setRequestModeration);

        Optional.ofNullable(updateEventAdminRequest.getStateAction()).ifPresent(stateAction -> {
            if (stateAction.equals(StateActionAdmin.PUBLISH_EVENT) && !event.getState().equals(StateEvent.PENDING)) {
                throw new ConflictException(String.format(
                        "Событие можно публиковать только, если оно в состоянии ожидания публикации, eventId = %s, updateEventAdminRequest: %s.",
                        eventId, updateEventAdminRequest));
            }
            if (stateAction.equals(StateActionAdmin.REJECT_EVENT) && event.getState().equals(StateEvent.PUBLISHED)) {
                throw new ConflictException("The event cannot be rejected");
            }

            switch (stateAction) {
                case PUBLISH_EVENT -> {
                    event.setState(StateEvent.PUBLISHED);
                    event.setPublishedOn(LocalDateTime.now());
                }
                case REJECT_EVENT -> event.setState(StateEvent.CANCELED);
            }
        });

        Optional.ofNullable(updateEventAdminRequest.getTitle()).ifPresent(event::setTitle);

        try {
            return EventMapper.INSTANCE.toEventFullDto(eventRepository.saveAndFlush(event));
        } catch (DataIntegrityViolationException e) {
            throw new NotSaveException("Event don't update");
        }
    }

}