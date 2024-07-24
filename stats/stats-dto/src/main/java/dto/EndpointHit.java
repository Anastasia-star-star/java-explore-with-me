package dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static constant.Constants.YYYY_MM_DD_HH_MM_SS;

@Data
public class EndpointHit {

    private Long id;

    @NotBlank(message = "App name must not be blank.")
    private String app;

    @NotBlank(message = "URI must not be blank.")
    private String uri;

    @NotBlank(message = "IP-address must not be blank.")
    private String ip;

    @NotNull(message = "Date and time must not be null.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = YYYY_MM_DD_HH_MM_SS)
    private String timestamp;

}