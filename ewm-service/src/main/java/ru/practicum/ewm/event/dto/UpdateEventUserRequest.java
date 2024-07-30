package ru.practicum.ewm.event.dto;

import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.event.model.StateActionUser;

import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000, message =
            "Ошибка! Краткое описание события может содержать минимум 20, максимум 2000 символов.")
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, message =
            "Ошибка! Полное описание события может содержать минимум 20, максимум 7000 символов.")
    private String description;

    @FutureOrPresent(message = "Ошибка! Дата события должна еще не наступить.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private StateActionUser stateAction;

    @Length(min = 3, max = 120, message = "Event title should be between 3 and 120 characters.")
    private String title;

}