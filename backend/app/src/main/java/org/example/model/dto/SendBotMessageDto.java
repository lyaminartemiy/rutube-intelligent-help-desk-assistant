package org.example.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SendBotMessageDto(
        String chatId,
        @Nullable String text,
        Boolean isAnswer
) {
}
