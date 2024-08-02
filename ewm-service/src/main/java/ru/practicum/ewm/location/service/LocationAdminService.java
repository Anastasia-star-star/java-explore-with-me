package ru.practicum.ewm.location.service;

import ru.practicum.ewm.location.dto.LocationDto;

import java.util.List;

public interface LocationAdminService {

    LocationDto updateLocation(Long locId, LocationDto locationDto);

    List<LocationDto> getAllLocations(Integer from, Integer size);

    LocationDto getLocationById(Long locId);

    LocationDto saveLocation(LocationDto locationDto);

    Boolean deleteLocationById(Long locId);
}