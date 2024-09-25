package org.example.model.dto;

import org.example.model.entity.Employee;

public record SignUpData(
        String fullName,
        String email,
        Employee.Role role
) {
}
