package ru.practicum.ewm.event.repository;

import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiatorId(Long userId, Pageable page);

    @Query("SELECT e FROM Event e " +
            "WHERE (:users IS NULL OR e.initiator.id IN :users) " +
            "AND (:states IS NULL OR e.state IN :states) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd")
    List<Event> getAllEventsByAdmin(@Param("users") List<Long> users,
                                    @Param("states") List<StateEvent> states,
                                    @Param("categories") List<Long> categories,
                                    @Param("rangeStart") LocalDateTime rangeStart,
                                    @Param("rangeEnd") LocalDateTime rangeEnd,
                                    Pageable page);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = 'PUBLISHED' " +
            "AND (LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd " +
            "AND (:onlyAvailable = FALSE OR (e.participantLimit = 0 OR e.participantLimit > e.confirmedRequests))")
    List<Event> getAllEvents(@Param("text") String text,
                             @Param("categories") List<Long> categories,
                             @Param("paid") Boolean paid,
                             @Param("rangeStart") LocalDateTime rangeStart,
                             @Param("rangeEnd") LocalDateTime rangeEnd,
                             @Param("onlyAvailable") Boolean onlyAvailable,
                             Pageable page);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = 'PUBLISHED' " +
            "AND function('distance', e.location.lat, e.location.lon, :lat, :lon) <= :radius " +
            "AND e.eventDate BETWEEN :rangeStart AND :rangeEnd")
    List<Event> getAllEventsByLocation(@Param("lat") Float lat,
                                       @Param("lon") Float lon,
                                       @Param("radius") Float radius,
                                       @Param("rangeStart") LocalDateTime rangeStart,
                                       @Param("rangeEnd") LocalDateTime rangeEnd,
                                       Pageable page);
}
