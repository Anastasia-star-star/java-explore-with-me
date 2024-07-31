package ru.practicum.ewm.event.repository;

import ru.practicum.ewm.event.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLatAndLon(Float lat, Float lon);

}