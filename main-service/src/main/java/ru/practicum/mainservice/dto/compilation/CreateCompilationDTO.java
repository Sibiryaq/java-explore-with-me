package ru.practicum.mainservice.dto.compilation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCompilationDTO {
    private Set<Integer> events;
    private Boolean pinned = false;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
}
