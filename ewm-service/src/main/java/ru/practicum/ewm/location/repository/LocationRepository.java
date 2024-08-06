package ru.practicum.ewm.location.repository;

import ru.practicum.ewm.location.model.Location;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLatAndLonAndRadius(Float lat, Float lon, Float radius);

    @Modifying
    @Query("DELETE FROM Location loc WHERE loc.id = ?1")
    Integer deleteByIdWithReturnedLines(Long locId);

    List<Location> findByRadiusIsGreaterThan(Float radius, Pageable page);

}