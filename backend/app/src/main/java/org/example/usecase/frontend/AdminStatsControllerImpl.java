package org.example.usecase.frontend;

import org.example.model.dto.AIProcessedRequestPercentageChart;
import org.example.model.dto.AdminEmployeeStats;
import org.example.model.dto.AdminRequestsStats;
import org.example.model.dto.DailyPercentageOfRequestsHandledByAIChartData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin-stats")
public class AdminStatsControllerImpl implements AdminStatsController {

    @GetMapping("/employee/stats")
    @Override
    public AdminEmployeeStats getAdminEmployeeStats(){
        return new AdminEmployeeStats(
                getEmployeeCount(),
                getOnlineEmployeeCount()
        );
    }

    @Override
    @GetMapping("/employee/count")
    public Long getEmployeeCount() {
        return 0L;
    }

    @Override
    @GetMapping("/employee/count/online")
    public Long getOnlineEmployeeCount() {
        return 0L;
    }

    @GetMapping("/requests/stats")
    @Override
    public AdminRequestsStats getRequestStats(){
        return new AdminRequestsStats(
                getPercentageOfRequestsHandledByAI(),
                getPercentageOfRequestsHandledByEmployees(),
                getInProgressRequestsCount(),
                getUnassignedRequestsCount()
        );
    }

    @Override
    @GetMapping("/requests/ai-handled/percentage")
    public Double getPercentageOfRequestsHandledByAI() {
        return 0.0;
    }

    @Override
    @GetMapping("/requests/employee-handled/percentage")
    public Double getPercentageOfRequestsHandledByEmployees() {
        return 0.0;
    }

    @Override
    @GetMapping("/requests/in-progress/count")
    public Long getInProgressRequestsCount() {
        return 0L;
    }

    @Override
    @GetMapping("/requests/unassigned/count")
    public Long getUnassignedRequestsCount() {
        return 0L;
    }

    @Override
    @GetMapping("/chart/daily-percentage-of-requests-handled-by-ai")
    public List<DailyPercentageOfRequestsHandledByAIChartData> getDailyPercentageOfRequestsHandledByAIChart() {
        return List.of();
    }

    @Override
    @GetMapping("/chart/ai-processed-request-percentage")
    public List<AIProcessedRequestPercentageChart> getAIProcessedRequestPercentageChart() {
        return List.of();
    }
}
