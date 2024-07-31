package ru.practicum.ewm.compilation.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCompilationDto {

    private Set<Long> events;

    private Boolean pinned = false;

    @NotBlank(message = "title can not be blank")
    @Length(min = 1, max = 50, message = "Compilation title must be between 1 and 50 characters.")
    private String title;

}