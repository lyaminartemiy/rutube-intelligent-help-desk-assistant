package org.example.model.dto;

public record EmployeeDto(
        Long id,
        String fullName,
        Long inProgressRequestCount,
        Long ClosedRequestCount,
        Boolean isOnline
) {
}
