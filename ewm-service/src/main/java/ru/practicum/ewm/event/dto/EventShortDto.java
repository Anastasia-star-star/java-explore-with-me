package ru.practicum.ewm.event.dto;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.time.LocalDateTime;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class EventShortDto {

    private Long id; // Идентификатор

    private String annotation; // Краткое описание

    private CategoryDto category; // Категория

    private Long confirmedRequests; // Количество одобренных заявок на участие в данном событии

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime eventDate;
    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")

    private UserShortDto initiator;

    private Boolean paid; // Нужно ли оплачивать участие в событии

    private String title; // Заголовок

    private Long views; // Количество просмотрев события

}