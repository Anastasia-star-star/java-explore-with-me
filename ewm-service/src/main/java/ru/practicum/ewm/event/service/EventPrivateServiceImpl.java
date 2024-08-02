package ru.practicum.ewm.event.service;

import ru.practicum.ewm.category.model.Category;
import dto.ViewStats;
import ru.practicum.ewm.event.dto.creating.NewEventDto;
import ru.practicum.ewm.event.dto.info.EventFullDto;
import ru.practicum.ewm.event.dto.info.EventShortDto;
import ru.practicum.ewm.event.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.event.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.event.dto.update.UpdateEventUserRequest;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotSaveException;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.request.model.StateRequest;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.util.UtilService;
import ru.practicum.ewm.util.Statistic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static constant.Constants.FORMATTER_FOR_DATETIME;

@Service
@Transactional
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {

    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final UtilService utilService;
    private final Statistic statistic;

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getAllEventsByUser(Long userId, Integer from, Integer size) {
        utilService.returnUser(userId);
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        return EventMapper.INSTANCE.convertEventListToEventShortDtoList(
                eventRepository.findByInitiatorId(userId, page));
    }

    @Override
    public EventFullDto saveEvent(Long userId, NewEventDto newEventDto) {
        User user = utilService.returnUser(userId);
        Category category = utilService.returnCategory(newEventDto.getCategory());
        if (!newEventDto.getEventDate().isAfter(LocalDateTime.now())) {
            throw new ConflictException("Field: eventDate. Error: должно содержать дату, которая еще не наступила. " +
                    "Value: " + newEventDto.getEventDate());
        }
        Location location = utilService.returnLocation(newEventDto.getLocation());

        Event event = EventMapper.INSTANCE.toEventFromNewDto(newEventDto, user, category, location);
        event.setConfirmedRequests(0L);
        event.setConfirmedRequests(0L);
        event.setCreatedOn(LocalDateTime.now());
        event.setLocation(location);
        event.setPublishedOn(LocalDateTime.now());
        event.setState(StateEvent.PENDING);

        try {
            event = eventRepository.save(event);
            EventFullDto eventFullDto = EventMapper.INSTANCE.toEventFullDto(event);
            eventFullDto.setViews(0L);
            return eventFullDto;
        } catch (DataIntegrityViolationException e) {
            throw new NotSaveException("Событие не было создано: " + newEventDto);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getEventById(Long userId, Long eventId) {
        utilService.returnUser(userId);
        Event event = utilService.returnEvent(eventId);
        utilService.checkEventInitiator(event, userId);

        List<String> uris = List.of("/events/" + eventId);
        List<ViewStats> viewStats = statistic.statsClient.getAllStats(
                LocalDateTime.now().minusYears(100).format(FORMATTER_FOR_DATETIME),
                LocalDateTime.now().plusYears(100).format(FORMATTER_FOR_DATETIME), uris, true);

        EventFullDto eventFullDto = EventMapper.INSTANCE.toEventFullDto(event);
        eventFullDto.setConfirmedRequests(requestRepository
                .countByEventIdAndStatus(eventId, StateRequest.CONFIRMED));
        eventFullDto.setViews(viewStats.isEmpty() ? 0L : viewStats.get(0).getHits());

        return eventFullDto;
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        utilService.returnUser(userId);
        Event event = utilService.returnEvent(eventId);
        utilService.checkEventInitiator(event, userId);

        if (event.getState().equals(StateEvent.PUBLISHED)) {
            throw new ConflictException(String.format("Событие не должно быть опубликовано, userId = %s, " +
                    "eventId = %s, updateEventUserRequest: %s.", userId, eventId, updateEventUserRequest));
        }

        Optional.ofNullable(updateEventUserRequest.getAnnotation()).ifPresent(event::setAnnotation);

        Optional.ofNullable(updateEventUserRequest.getCategory())
                .map(utilService::returnCategory)
                .ifPresent(event::setCategory);

        Optional.ofNullable(updateEventUserRequest.getDescription()).ifPresent(event::setDescription);

        Optional.ofNullable(updateEventUserRequest.getEventDate())
                .ifPresent(eventDate -> {
                    if (eventDate.plusHours(2).isBefore(LocalDateTime.now())) {
                        throw new BadRequestException(String.format("Дата и время на которые намечено событие " +
                                "не может быть раньше, чем через два часа от текущего момента, userId = %s, eventId = %s, " +
                                "updateEventUserRequest: %s.", userId, eventId, updateEventUserRequest));
                    }
                    event.setEventDate(eventDate);
                });

        Optional.ofNullable(updateEventUserRequest.getLocation())
                .map(utilService::returnLocation)
                .ifPresent(event::setLocation);

        Optional.ofNullable(updateEventUserRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(updateEventUserRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(updateEventUserRequest.getRequestModeration()).ifPresent(event::setRequestModeration);

        Optional.ofNullable(updateEventUserRequest.getStateAction()).ifPresent(stateAction -> {
            if (!event.getState().equals(StateEvent.PENDING) && !event.getState().equals(StateEvent.CANCELED)) {
                throw new ConflictException(String.format("Изменить можно только отмененные события или события " +
                        "в состоянии ожидания модерации, userId = %s, eventId = %s, " +
                        "updateEventUserRequest: %s.", userId, eventId, updateEventUserRequest));
            }
            switch (stateAction) {
                case CANCEL_REVIEW:
                    event.setState(StateEvent.CANCELED);
                    break;
                case SEND_TO_REVIEW:
                    event.setState(StateEvent.PENDING);
                    break;
            }
        });

        Optional.ofNullable(updateEventUserRequest.getTitle()).ifPresent(event::setTitle);

        try {
            return EventMapper.INSTANCE.toEventFullDto(event);
        } catch (DataIntegrityViolationException e) {
            throw new NotSaveException("do not save event");
        }
    }


    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> getAllRequestsOfEventByUser(Long userId, Long eventId) {
        utilService.returnUser(userId);
        Event event = utilService.returnEvent(eventId);
        utilService.checkEventInitiator(event, userId);

        return ParticipationRequestMapper.INSTANCE.convertParticipationRequestToDtoList(
                requestRepository.findAllByEventId(eventId));
    }

    @Override
    public EventRequestStatusUpdateResult updateAllRequestsOfEventByUser(Long userId, Long eventId,
                                     EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        utilService.returnUser(userId);
        Event event = utilService.returnEvent(eventId);
        utilService.checkEventInitiator(event, userId);

        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            throw new ConflictException("У события лимит заявок равен 0 или отключена пре-модерация, userId = "
                    + userId + ", eventId = " + eventId + ", eventRequestStatusUpdateRequest: "
                    + eventRequestStatusUpdateRequest);
        }
        long confirmedLimit = requestRepository.countByEventIdAndStatus(eventId, StateRequest.CONFIRMED);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= confirmedLimit) {
            throw new ConflictException("Нельзя подтвердить заявку, так как достигнут лимит по заявкам " +
                    "на данное событие, userId = " + userId + ", eventId = " + eventId +
                    ", eventRequestStatusUpdateRequest: " + eventRequestStatusUpdateRequest);
        }

        List<ParticipationRequest> requestsToUpdate = requestRepository
                .findAllByIdIn(eventRequestStatusUpdateRequest.getRequestIds());
        List<ParticipationRequest> confirmed = new ArrayList<>();
        List<ParticipationRequest> rejected = new ArrayList<>();

        for (ParticipationRequest request : requestsToUpdate) {
            if (!request.getStatus().equals(StateRequest.PENDING)) {
                throw new ConflictException("Нельзя отменить уже принятую заявку на участие, userId = " + userId
                        + ", eventId = " + eventId + ", eventRequestStatusUpdateRequest: "
                        + eventRequestStatusUpdateRequest);
            }
            if (!request.getEvent().getId().equals(eventId)) {
                rejected.add(request);
                continue;
            }

            switch (eventRequestStatusUpdateRequest.getStatus()) {
                case CONFIRMED:
                    if (confirmedLimit < event.getParticipantLimit()) {
                        request.setStatus(StateRequest.CONFIRMED);
                        confirmedLimit++;
                        confirmed.add(request);
                    } else {
                        request.setStatus(StateRequest.REJECTED);
                        rejected.add(request);
                    }
                    break;

                case REJECTED:
                    request.setStatus(StateRequest.REJECTED);
                    rejected.add(request);
                    break;
            }
        }

        requestRepository.saveAll(requestsToUpdate);
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        result.setConfirmedRequests(ParticipationRequestMapper.INSTANCE
                .convertParticipationRequestToDtoList(confirmed));
        result.setRejectedRequests(ParticipationRequestMapper.INSTANCE
                .convertParticipationRequestToDtoList(rejected));

        return result;
    }

}