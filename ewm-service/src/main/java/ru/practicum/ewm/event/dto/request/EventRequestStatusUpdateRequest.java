package ru.practicum.ewm.event.dto.request;

import ru.practicum.ewm.event.model.StateStatusRequest;

import java.util.List;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EventRequestStatusUpdateRequest {

    @NotNull(message = "Request ids should not be undefined.")
    private List<Long> requestIds;

    @NotNull(message = "Request status should not be undefined.")
    private StateStatusRequest status;

}