package org.example.model.dto;

import java.util.List;

public record TechSupportRequestDto(
        String title,
        Long id,
        Boolean isInProgress,
        List<String> assignees
) {
}
