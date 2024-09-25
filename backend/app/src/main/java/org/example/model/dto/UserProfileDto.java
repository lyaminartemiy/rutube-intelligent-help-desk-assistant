package org.example.model.dto;

public record UserProfileDto(
        String fullName,
        String roleName,
        String email
) {
}
