package org.example.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;

import java.time.ZonedDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UpdateMessageDto(
        String chatId,
        String messageId,
        String text,
        @Nullable ZonedDateTime createdAt,
        @Nullable Boolean isHelpful,
        String aiText
) {
}
