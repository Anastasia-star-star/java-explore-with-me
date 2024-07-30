package ru.practicum.ewm.event.dto.info;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.dto.creating.LocationDto;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class EventFullDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private Long confirmedRequests;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private StateEvent state;

    private String title;

    private Long views;

}