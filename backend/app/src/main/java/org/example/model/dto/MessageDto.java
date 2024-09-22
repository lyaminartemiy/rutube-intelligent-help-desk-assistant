package org.example.model.dto;

import jakarta.annotation.Nullable;
import org.example.model.entity.Message;

import java.time.ZonedDateTime;

public record MessageDto(
        String messageText,
        ZonedDateTime createdAt,
        Message.Side side,
        @Nullable Boolean isHelpful) {
}
