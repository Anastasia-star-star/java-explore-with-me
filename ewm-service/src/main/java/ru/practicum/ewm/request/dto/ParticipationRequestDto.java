package ru.practicum.ewm.request.dto;

import ru.practicum.ewm.request.model.StateRequest;

import java.time.LocalDateTime;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class ParticipationRequestDto {

    private Long id; // Идентификатор заявки

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime created; // Дата и время создания заявки

    private Long event; // Идентификатор события

    private Long requester; // Идентификатор пользователя, отправившего заявку

    private StateRequest status; // Статус заявки

}