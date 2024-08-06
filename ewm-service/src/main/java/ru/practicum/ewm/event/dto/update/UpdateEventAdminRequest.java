package ru.practicum.ewm.event.dto.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.event.model.StateActionAdmin;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import ru.practicum.ewm.location.dto.LocationDto;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class UpdateEventAdminRequest {

    @Length(min = 20, max = 2000, message = "Event annotation should be between 20 and 2000 characters.")
    private String annotation;

    private Long category;

    @Length(min = 20, max = 7000, message = "Event description should be between 20 and 7000 characters.")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)

    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    @PositiveOrZero(message = "Event participant limit should be greater than 0 or equal to 0.")
    private Integer participantLimit;

    private Boolean requestModeration;

    private StateActionAdmin stateAction;

    @Length(min = 3, max = 120, message = "Event title should be between 3 and 120 characters.")
    private String title;

}