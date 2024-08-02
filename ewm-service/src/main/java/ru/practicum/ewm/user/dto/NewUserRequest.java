package ru.practicum.ewm.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewUserRequest {

    @NotBlank(message = "User name must not be blank.")
    @Length(min = 2, max = 250, message = "User name must be between 2 and 250 characters.")
    private String name;

    @NotBlank(message = "User email must not be blank.")
    @Size(min = 6, max = 254, message = "User email must be between 6 and 250 characters.")
    @Email(message = "User email is not valid.")
    private String email;
}