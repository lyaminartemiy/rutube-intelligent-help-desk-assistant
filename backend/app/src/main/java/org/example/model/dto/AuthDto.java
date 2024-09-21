package org.example.model.dto;

import org.example.model.notDto.EmployeeRole;

public record AuthDto(
        String jwt,
        EmployeeRole role
) {
}
