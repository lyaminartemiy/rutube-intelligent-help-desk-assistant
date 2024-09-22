package org.example.model.dto;

public record EmployeeStats(
        Long totalRequestsHandled,
        Long inProgressRequestsCount,
        Long closedRequestsCountToday,
        Long closedRequestsCountThisWeek,
        Long closedRequestsCountThisMonth
) {}
