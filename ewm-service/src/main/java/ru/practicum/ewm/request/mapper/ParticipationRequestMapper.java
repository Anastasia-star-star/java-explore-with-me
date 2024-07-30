package ru.practicum.ewm.request.mapper;

import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.ParticipationRequest;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Mapper(uses = {EventMapper.class, UserMapper.class})
public interface ParticipationRequestMapper {

    ParticipationRequestMapper INSTANCE = Mappers.getMapper(ParticipationRequestMapper.class);

    @Mapping(target = "created", source = "participationRequest.created", dateFormat = YYYY_MM_DD_HH_MM_SS)
    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);

    List<ParticipationRequestDto> convertParticipationRequestToDtoList(List<ParticipationRequest> list);

}