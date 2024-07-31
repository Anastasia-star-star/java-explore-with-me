package ru.practicum.ewm.compilation.dto;

import java.util.Set;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateCompilationRequest {

    private Set<Long> events;

    private Boolean pinned = false;

    @NotBlank
    @Length(min = 1, max = 50, message = "Compilation title must be between 1 and 50 characters.")
    private String title;

}