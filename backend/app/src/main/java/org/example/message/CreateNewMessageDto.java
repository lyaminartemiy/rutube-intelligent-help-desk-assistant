package org.example.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.ZonedDateTime;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateNewMessageDto(
        String chatId,
        String text,
        List<AdditionalContentDto> additionalContent,
        ZonedDateTime createdAt
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record AdditionalContentDto(
            AdditionalContent.Type contentType,
            byte[] fileBytes,
            String fileExtension) {}
}
