package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.EndpHit;
import ru.practicum.ewm.model.Hit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Mapper
public interface StatsServerMapper {

    StatsServerMapper INSTANCE = Mappers.getMapper(StatsServerMapper.class);

    @Mapping(target = "timestamp", source = "hit.timestamp", dateFormat = YYYY_MM_DD_HH_MM_SS)
    EndpHit toEndpointHit(Hit hit);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", source = "endpointHit.timestamp", dateFormat = YYYY_MM_DD_HH_MM_SS)
    Hit toHit(EndpHit endpHit);

}