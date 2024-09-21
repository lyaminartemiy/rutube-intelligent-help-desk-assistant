package org.example.model.dto;

import java.util.List;

public record TechSupportRequestDto(
        String title,
        String id,
        Boolean isInProgress,
        List<String> assignees
) {
}
