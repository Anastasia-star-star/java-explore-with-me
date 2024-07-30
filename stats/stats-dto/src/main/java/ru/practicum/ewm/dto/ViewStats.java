package ru.practicum.ewm.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {

    private String app;

    private String uri;

    private Long hits;

}