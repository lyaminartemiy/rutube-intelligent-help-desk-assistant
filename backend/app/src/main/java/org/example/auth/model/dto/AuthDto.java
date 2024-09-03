package org.example.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.User;
import org.springframework.security.core.GrantedAuthority;

@Schema(description = "Authentication data transfer object")
public record AuthDto(
        @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...") String jwt,
        @Schema(description = "User role") GrantedAuthority authority
) {
}
