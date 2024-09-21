package org.example.model.dto;

import jakarta.annotation.Nullable;
import org.example.model.notDto.MessageSide;

import java.time.ZonedDateTime;

public record MessageDto(
        String messageText,
        ZonedDateTime createdAt,
        MessageSide side,
        @Nullable Boolean isHelpful) {
}
