package ru.practicum.ewm.event.dto.update;

import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.event.dto.creating.LocationDto;
import ru.practicum.ewm.event.model.StateActionAdmin;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class UpdateEventAdminRequest {

    @Length(min = 20, max = 2000, message = "Event annotation should be between 20 and 2000 characters.")
    @NotNull
    private String annotation;

    @NotNull
    private Long category;

    @Length(min = 20, max = 7000, message = "Event description should be between 20 and 7000 characters.")

    @NotNull
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)

    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private LocationDto location;

    @NotNull
    private Boolean paid;

    @NotNull
    @PositiveOrZero(message = "Event participant limit should be greater than 0 or equal to 0.")
    private Integer participantLimit;

    @NotNull
    private Boolean requestModeration;

    @NotNull
    private StateActionAdmin stateAction;

    @NotNull
    @Length(min = 3, max = 120, message = "Event title should be between 3 and 120 characters.")
    private String title;

}