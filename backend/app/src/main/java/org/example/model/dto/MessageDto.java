package org.example.model.dto;

import java.time.ZonedDateTime;

public record MessageDto(
        String message,
        ZonedDateTime createdAt) {

}
