package org.example.usecase.frontend;

import org.example.model.dto.EmployeeStats;
import org.example.service.EmployeeStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.ZonedDateTime;
@RestController
@RequestMapping("/api/employee-stats")
public class EmployeeStatsControllerImpl implements EmployeeStatsController {

    private final EmployeeStatsService employeeStatsService;

    public EmployeeStatsControllerImpl(EmployeeStatsService employeeStatsService) {
        this.employeeStatsService = employeeStatsService;
    }

    @Override
    @GetMapping("/requests/summary")
    public EmployeeStats getEmployeeStats(Principal principal) {
        return new EmployeeStats(
                getTotalRequestsHandledByEmployee(principal),
                getInProgressRequestsCountByEmployee(principal),
                getClosedRequestsCountTodayByEmployee(principal),
                getClosedRequestsCountThisWeekByEmployee(principal),
                getClosedRequestsCountThisMonthByEmployee(principal)
        );
    }

    @Override
    @GetMapping("/requests/handled/count")
    public Long getTotalRequestsHandledByEmployee(Principal principal) {
        return employeeStatsService.getTotalRequestsHandledByEmployee(principal.getName());
    }

    @Override
    @GetMapping("/requests/in-progress/count")
    public Long getInProgressRequestsCountByEmployee(Principal principal) {
        return employeeStatsService.getInProgressRequestsCountByEmployee(principal.getName());
    }

    @Override
    @GetMapping("/requests/closed/count/today")
    public Long getClosedRequestsCountTodayByEmployee(Principal principal) {
        return employeeStatsService.getClosedRequestsCountTodayByEmployee(principal.getName());
    }

    @Override
    @GetMapping("/requests/closed/count/week")
    public Long getClosedRequestsCountThisWeekByEmployee(Principal principal) {
        return employeeStatsService.getClosedRequestsCountThisWeekByEmployee(principal.getName());
    }

    @Override
    @GetMapping("/requests/closed/count/month")
    public Long getClosedRequestsCountThisMonthByEmployee(Principal principal) {
        return employeeStatsService.getClosedRequestsCountThisMonthByEmployee(principal.getName());
    }

    @Override
    @GetMapping("/requests/closed/count/interval")
    public Long getClosedRequestsCountByDateForEmployee(Principal principal,
                                                        @RequestParam ZonedDateTime startDate,
                                                        @RequestParam ZonedDateTime endDate) {
        return employeeStatsService.getClosedRequestsCountByDateForEmployee(principal.getName(), startDate, endDate);
    }
}

