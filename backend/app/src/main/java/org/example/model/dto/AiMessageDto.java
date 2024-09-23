package org.example.model.dto;

import jakarta.annotation.Nullable;
import org.example.model.entity.Message;

import java.time.ZonedDateTime;

public record AiMessageDto(
        String text,
        ZonedDateTime createdAt,
        Message.Side side,
        @Nullable Boolean isHelpful) {
}
