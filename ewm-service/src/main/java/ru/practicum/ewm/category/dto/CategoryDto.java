package ru.practicum.ewm.category.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Category name must not be blank.")
    @Length(max = 50, message = "Category name must be less than 50 characters.")
    private String name;

}