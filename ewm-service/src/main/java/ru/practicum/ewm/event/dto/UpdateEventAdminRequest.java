package ru.practicum.ewm.event.dto;

import ru.practicum.ewm.event.model.StateActionAdmin;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class UpdateEventAdminRequest {

    @Size(min = 20, max = 2000, message =
            "Ошибка! Краткое описание события может содержать минимум 20, максимум 2000 символов.")
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, message =
            "Ошибка! Полное описание события может содержать минимум 20, максимум 7000 символов.")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private StateActionAdmin stateAction; // Изменение состояния события

    @Size(min = 3, max = 120, message =
            "Ошибка! Заголовок события может содержать минимум 3, максимум 120 символов.")
    private String title;

}