package ru.practicum.mainservice.dto.compilation;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UpdateCompilationDTO {
    private Set<Integer> events;
    private Boolean pinned = false;
    @Size(min = 1, max = 50)
    private String title;
}
