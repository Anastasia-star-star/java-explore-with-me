package ru.practicum.ewm.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotSaveException;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.mapper.LocationMapper;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.location.repository.LocationRepository;
import ru.practicum.ewm.util.UtilService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

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

        Optional.ofNullable(locationDto.getLat()).ifPresent(location::setLat);
        Optional.ofNullable(locationDto.getLon()).ifPresent(location::setLon);
        Optional.ofNullable(locationDto.getRadius()).ifPresent(location::setRadius);

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