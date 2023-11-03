package ru.practicum.mainservice.dto.compilation;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
public class UpdateCompilationDTO {
    @UniqueElements
    private Set<Integer> events = Collections.emptySet();
    private Boolean pinned = false;
    @Size(min = 1, max = 50)
    private String title;
}
