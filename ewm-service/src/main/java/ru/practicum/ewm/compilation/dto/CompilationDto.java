package ru.practicum.ewm.compilation.dto;

import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.Set;

import lombok.Data;

@Data
public class CompilationDto {

    private Long id; // Идентификатор

    private Set<EventShortDto> events; // Список идентификаторов событий, входящих в подборку

    private Boolean pinned; // Закреплена ли подборка на главной странице сайта

    private String title; // Заголовок подборки

}