package org.example.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
import org.example.model.entity.Message;

import java.time.ZonedDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AiMessageDto(
        String text,
        ZonedDateTime createdAt,
        Message.Side side,
        @Nullable Boolean isHelpful) {
}
