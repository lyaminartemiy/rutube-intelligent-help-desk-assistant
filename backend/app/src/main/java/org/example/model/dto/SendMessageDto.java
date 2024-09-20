package org.example.model.dto;

public record SendMessageDto(
        String chatId,
        String text
) {
}
