package ru.practicum.ewm.compilation.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCompilationDto {

    private Set<Long> events;

    private Boolean pinned = false;

    @NotBlank(message = "Ошибка! Заголовок подборки не может быть пустым.")
    @Length(min = 1, max = 50, message = "Compilation title must be between 1 and 50 characters.")
    private String title;

}