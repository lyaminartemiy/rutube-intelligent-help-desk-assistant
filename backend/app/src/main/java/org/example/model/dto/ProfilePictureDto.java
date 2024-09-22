package org.example.model.dto;

public record ProfilePictureDto(
        String fileName,
        byte[] fileBytes
) {
}
