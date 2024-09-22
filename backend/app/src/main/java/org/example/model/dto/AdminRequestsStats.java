package org.example.model.dto;

public record AdminRequestsStats(
        Double aiHandledPercentage,
        Double employeeHandledPercentage,
        Long inProgressRequestsCount,
        Long unassignedRequestsCount
) {}