package org.example.usecase.frontend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/employee-stats")
public class EmployeeStatsControllerImpl implements EmployeeStatsController {
    @Override
    @GetMapping("/requests/handled/count")
    public Long getTotalRequestsHandledByEmployee(Principal principal) {
        return 0L;
    }

    @Override
    @GetMapping("/requests/in-progress/count")
    public Long getInProgressRequestsCountByEmployee(Principal principal) {
        return 0L;
    }

    @Override
    @GetMapping("/requests/closed/count/today")
    public Long getClosedRequestsCountTodayByEmployee(Principal principal) {
        return 0L;
    }

    @Override
    @GetMapping("/requests/closed/count/week")
    public Long getClosedRequestsCountThisWeekByEmployee(Principal principal) {
        return 0L;
    }

    @Override
    @GetMapping("/requests/closed/count/month")
    public Long getClosedRequestsCountThisMonthByEmployee(Principal principal) {
        return 0L;
    }

    @Override
    @GetMapping("/requests/closed/count/interval")
    public Long getClosedRequestsCountByDateForEmployee(Principal principal, @RequestParam ZonedDateTime startDate, @RequestParam ZonedDateTime endDate) {
        return 0L;
    }
}
