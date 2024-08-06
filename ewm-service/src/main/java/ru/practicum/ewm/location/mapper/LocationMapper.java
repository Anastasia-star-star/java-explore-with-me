package ru.practicum.ewm.location.mapper;

import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.model.Location;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    @Mapping(target = "id", ignore = true)
    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);

    List<LocationDto> convertLocationListToLocationDTOList(List<Location> list);

}