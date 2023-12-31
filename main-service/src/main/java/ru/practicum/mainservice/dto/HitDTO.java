package ru.practicum.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HitDTO {
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
