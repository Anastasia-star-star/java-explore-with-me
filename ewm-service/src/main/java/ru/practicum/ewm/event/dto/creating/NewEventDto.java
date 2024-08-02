package ru.practicum.ewm.event.dto.creating;

import java.time.LocalDateTime;
import javax.validation.constraints.*;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class NewEventDto {

    @NotBlank(message = "Event annotation should not be blank.")
    @Length(min = 20, max = 2000, message = "Event annotation should be between 20 and 2000 characters.")
    private String annotation;

    @NotNull(message = "Event category should not be undefined.")
    private Long category;

    @NotBlank(message = "Event description should not be blank.")
    @Length(min = 20, max = 7000, message = "Event description should be between 20 and 7000 characters.")
    private String description;

    @NotNull(message = "Event event date should not be undefined.")
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;

    @NotNull(message = "Event location should not be undefined.")
    private LocationDto location;

    private Boolean paid = false;

    @PositiveOrZero(message = "Event participant limit should be greater than 0 or equal to 0.")
    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

    @NotBlank(message = "Event title should not be blank.")
    @Length(min = 3, max = 120, message = "Event title should be between 3 and 120 characters.")
    private String title;

}