package ru.practicum.ewm.category.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryDto {

    private Long id;

    private String name;

}