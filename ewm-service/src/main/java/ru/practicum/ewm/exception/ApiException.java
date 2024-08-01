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
public class ApiException {

    private HttpStatus status;

    private String reason;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime timestamp;

    private List<String> errors;
}