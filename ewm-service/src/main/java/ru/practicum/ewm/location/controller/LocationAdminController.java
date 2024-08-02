package ru.practicum.ewm.location.controller;

import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.service.LocationAdminService;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationAdminController {

    private final LocationAdminService adminService;

    @PostMapping
    @Validated
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto saveLocation(@Valid @RequestBody LocationDto locationDto) {
        log.info("Adding new location");
        return adminService.saveLocation(locationDto);
    }

    @DeleteMapping("/{locId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteLocationById(@PathVariable Long locId) {
        log.info("Delete location");
        return adminService.deleteLocationById(locId);
    }

    @PatchMapping("/{locId}")
    public LocationDto updateLocation(@PathVariable Long locId, @RequestBody LocationDto locationDto) {
        log.info("update location");
        return adminService.updateLocation(locId, locationDto);
    }

    @GetMapping
    public List<LocationDto> getAllLocations(@PositiveOrZero
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("getting all Locations");
        return adminService.getAllLocations(from, size);
    }

    @GetMapping("/{locId}")
    public LocationDto getLocationById(@PathVariable Long locId) {
        log.info("get location by id");
        return adminService.getLocationById(locId);
    }

}