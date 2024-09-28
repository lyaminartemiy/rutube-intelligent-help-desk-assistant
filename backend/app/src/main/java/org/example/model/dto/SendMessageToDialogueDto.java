package org.example.model.dto;

public record SendMessageToDialogueDto(
        String text,
        Boolean isEditedByTechSupport
) {
}
