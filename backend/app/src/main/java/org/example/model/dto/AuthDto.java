package org.example.model.dto;

import org.example.model.entity.Employee;

public record AuthDto(
        String jwt,
        Employee.Role role
) {
}
