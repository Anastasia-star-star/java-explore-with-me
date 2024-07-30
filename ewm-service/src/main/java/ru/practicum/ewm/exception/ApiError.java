package ru.practicum.ewm.exception;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Getter
@Setter
@ToString
public class ApiError {

    private HttpStatus status; // Код статуса HTTP-ответа

    private String reason; // Общее описание причины ошибки

    private String message; // Сообщение об ошибке

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime timestamp; // Дата и время когда произошла ошибка

    private List<String> errors; // Список стектрейсов или описания ошибок

}