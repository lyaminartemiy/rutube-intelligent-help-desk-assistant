package org.example.service;

import org.example.model.entity.TechSupportRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.TechSupportRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

@Service
public class EmployeeStatsService {

    private final TechSupportRequestRepository techSupportRequestRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeStatsService(TechSupportRequestRepository techSupportRequestRepository,
                                EmployeeRepository employeeRepository) {
        this.techSupportRequestRepository = techSupportRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    public Long getClosedRequestsCountTodayByEmployee(String username) {
        ZonedDateTime startOfDay = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        return techSupportRequestRepository.countByAssignedEmployees_UsernameAndSession_ClosedAtAfter(username, startOfDay);
    }

    public Long getClosedRequestsCountThisWeekByEmployee(String username) {
        ZonedDateTime startOfWeek = ZonedDateTime.now().with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return techSupportRequestRepository.countByAssignedEmployees_UsernameAndSession_ClosedAtAfter(username, startOfWeek);
    }

    public Long getClosedRequestsCountThisMonthByEmployee(String username) {
        ZonedDateTime startOfMonth = ZonedDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return techSupportRequestRepository.countByAssignedEmployees_UsernameAndSession_ClosedAtAfter(username, startOfMonth);
    }

    public Long getClosedRequestsCountByDateForEmployee(String username, ZonedDateTime startDate, ZonedDateTime endDate) {
        return techSupportRequestRepository.countByAssignedEmployees_UsernameAndSession_ClosedAtBetween(username, startDate, endDate);
    }

    public Long getTotalRequestsHandledByEmployee(String name) {
        return techSupportRequestRepository.countByStatusAndAssignedEmployees_Username(TechSupportRequest.Status.CLOSED, name);
    }

    public Long getInProgressRequestsCountByEmployee(String name) {
        return techSupportRequestRepository.countByStatusAndAssignedEmployees_Username(TechSupportRequest.Status.IN_PROGRESS, name);
    }
}
