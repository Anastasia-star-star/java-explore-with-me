package ru.practicum.ewm.location.service;

import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotSaveException;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.mapper.LocationMapper;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.location.repository.LocationRepository;
import ru.practicum.ewm.util.UtilService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationAdminServiceImpl implements LocationAdminService {

    private final LocationRepository locationRepository;
    private final UtilService utilService;

    @Transactional
    @Override
    public LocationDto saveLocation(LocationDto locationDto) {
        try {
            Location location = locationRepository.save(
                    LocationMapper.INSTANCE.toLocation(locationDto));
            return LocationMapper.INSTANCE.toLocationDto(location);
        } catch (DataIntegrityViolationException e) {
            throw new NotSaveException("location can not be save");
        }
    }

    @Transactional
    @Override
    public Boolean deleteLocationById(Long locId) {
        utilService.returnLocationById(locId);

        try {
            return locationRepository.deleteByIdWithReturnedLines(locId) > 0;
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("location can not be deleted");
        }
    }

    @Transactional
    @Override
    public LocationDto updateLocation(Long locId, LocationDto locationDto) {
        Location location = utilService.returnLocationById(locId);

        if (locationDto.getLat() != null) {
            location.setLat(locationDto.getLat());
        }
        if (locationDto.getLon() != null) {
            location.setLon(locationDto.getLon());
        }
        if (locationDto.getRadius() != null) {
            location.setRadius(locationDto.getRadius());
        }

        try {
            return LocationMapper.INSTANCE.toLocationDto(location);
        } catch (DataIntegrityViolationException e) {
            throw new NotSaveException("location can not be deleted");
        }
    }

    @Override
    public List<LocationDto> getAllLocations(Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id"));
        return LocationMapper.INSTANCE.convertLocationListToLocationDTOList(
                locationRepository.findByRadiusIsGreaterThan(0f, page));
    }

    @Override
    public LocationDto getLocationById(Long locId) {
        return LocationMapper.INSTANCE.toLocationDto(utilService.returnLocationById(locId));
    }

}