package ru.practicum.ewm.event.dto.creating;

import java.time.LocalDateTime;
import javax.validation.constraints.*;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.event.dto.creating.LocationDto;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class NewEventDto {

    @NotBlank(message = "Ошибка! Краткое описание события не может быть пустым.")
    @Size(min = 20, max = 2000, message =
            "Ошибка! Краткое описание события может содержать минимум 20, максимум 2000 символов.")
    private String annotation;

    @NotNull(message = "Ошибка! id категории, к которой относится событие, не может быть пустым.")
    private Long category;

    @NotBlank(message = "Ошибка! Полное описание события не может быть пустым.")
    @Size(min = 20, max = 7000, message =
            "Ошибка! Полное описание события может содержать минимум 20, максимум 7000 символов.")
    private String description;

    @NotNull(message = "Ошибка! Дата и время, на которые намечено событие, не могут быть пустыми.")
    @FutureOrPresent(message = "Ошибка! Дата события должна еще не наступить.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;

    @NotNull(message = "Ошибка! Широта и долгота места проведения события не могут быть пустыми.")
    private LocationDto location;

    private Boolean paid = false;

    @PositiveOrZero(message = "Event participant limit should be greater than 0 or equal to 0.")
    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

    @NotBlank(message = "Ошибка! Заголовок события не может быть пустым.")
    @Length(min = 3, max = 120, message = "Event title should be between 3 and 120 characters.")
    private String title;

}