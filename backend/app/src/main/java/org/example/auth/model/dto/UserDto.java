package org.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.User;

@Schema(description = "User data transfer object")
public record UserDto(
        @Schema(description = "Username of the user", example = "john_doe") String username,
        @Schema(description = "Password of the user", example = "password123") String password,
        User.Role role,
        String fullName) {
}
