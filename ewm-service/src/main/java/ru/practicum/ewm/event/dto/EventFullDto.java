package ru.practicum.ewm.event.dto;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class EventFullDto {

    private Long id; // Идентификатор

    private String annotation; // Краткое описание

    private CategoryDto category; // Категория

    private Long confirmedRequests; // Количество одобренных заявок на участие в данном событии

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime createdOn; //  Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")

    private String description; // Полное описание события

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;
    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")

    private UserShortDto initiator;

    private LocationDto location; // Широта и долгота места проведения события

    private Boolean paid; // Нужно ли оплачивать участие в событии

    private Integer participantLimit;
    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime publishedOn;
    // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")

    private Boolean requestModeration; // Нужна ли пре-модерация заявок на участие

    private StateEvent state; // Список состояний жизненного цикла события

    private String title; // Заголовок

    private Long views; // Количество просмотрев события

}