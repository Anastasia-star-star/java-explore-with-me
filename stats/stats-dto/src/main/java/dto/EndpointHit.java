package dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;


@Data
public class EndpointHit {

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

    @NotNull(message = "Date and time must not be null.")

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

}