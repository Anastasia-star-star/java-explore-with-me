package ru.practicum.ewm.category.dto;

import javax.validation.constraints.*;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCategoryDto {

    @NotBlank(message = "Category name must not be blank.")
    @Length(max = 50, message = "Category name must be less than 50 characters.")
    private String name;

}