package ru.practicum.ewm.location.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class LocationDto {

    private Long id;

    @NotNull
    private Float lat;

    @NotNull
    private Float lon;

    @NotNull
    @Positive
    private Float radius = 0f;

}