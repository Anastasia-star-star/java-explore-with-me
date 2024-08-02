package ru.practicum.ewm.exception.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.practicum.ewm.exception.model.ApiException;

@Getter
@RequiredArgsConstructor
@ToString
public class ValidationErrorResponse {

    private final List<ApiException> errorResponses;
}