package org.example.model.dto;

public record SaveMessageDto(
        String chatId,
        String text
) {
}
