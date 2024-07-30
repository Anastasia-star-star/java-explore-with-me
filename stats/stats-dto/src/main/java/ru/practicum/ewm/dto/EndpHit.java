package ru.practicum.ewm.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class EndpHit {

    private Long id;

    @NotBlank(message = "App name must not be blank.")
    @Size(max = 64)
    private String app;

    @NotBlank(message = "URI must not be blank.")
    @Size(max = 256)
    private String uri;

    @NotBlank(message = "IP-address must not be blank.")
    @Size(max = 16)
    private String ip;

    @NotBlank(message = "timestamp must not be blanck")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private LocalDateTime timestamp;

}