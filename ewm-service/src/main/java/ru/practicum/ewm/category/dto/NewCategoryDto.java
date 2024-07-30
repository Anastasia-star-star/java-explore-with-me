package ru.practicum.ewm.category.dto;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class NewCategoryDto {

    @NotBlank(message = "Ошибка! Название категории не может быть пустым.")
    @Size(max = 50, message = "Ошибка! Название категории может содержать максимум 50 символов.")
    private String name; // Название категории

}