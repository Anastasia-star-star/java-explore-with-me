package ru.practicum.ewm.event.dto.creating;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LocationDto {

    private Long id;

    @NotNull(message = "lat should not be undefined.")
    private Float lat;

    @NotNull(message = "lon should not be undefined.")
    private Float lon;

}