package org.example.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SendTechSupportMessageDto(
        String chatId,
        @Nullable String text,
        String messageId
) {
}
