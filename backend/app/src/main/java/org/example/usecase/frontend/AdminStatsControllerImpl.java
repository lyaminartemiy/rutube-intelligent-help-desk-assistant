package org.example.usecase.frontend;

import org.example.model.dto.AIProcessedRequestPercentageChart;
import org.example.model.dto.AdminEmployeeStats;
import org.example.model.dto.AdminRequestsStats;
import org.example.model.dto.DailyPercentageOfRequestsHandledByAIChartData;
import org.example.service.AdminStatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin-stats")
public class AdminStatsControllerImpl implements AdminStatsController {

    private final AdminStatsService adminStatsService;

    public AdminStatsControllerImpl(AdminStatsService adminStatsService) {
        this.adminStatsService = adminStatsService;
    }

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
        return adminStatsService.getEmployeeCount();
    }

    @Override
    @GetMapping("/employee/count/online")
    public Long getOnlineEmployeeCount() {
        return adminStatsService.getOnlineEmployeeCount();
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
        return adminStatsService.getPercentageOfRequestsHandledByAI();
    }

    @Override
    @GetMapping("/requests/employee-handled/percentage")
    public Double getPercentageOfRequestsHandledByEmployees() {
        return adminStatsService.getPercentageOfRequestsHandledByEmployees();
    }

    @Override
    @GetMapping("/requests/in-progress/count")
    public Long getInProgressRequestsCount() {
        return adminStatsService.getInProgressRequestsCount();
    }

    @Override
    @GetMapping("/requests/unassigned/count")
    public Long getUnassignedRequestsCount() {
        return adminStatsService.getUnassignedRequestsCount();
    }

    @Override
    @GetMapping("/chart/plot-percentage-of-requests-handled-by-ai")
    public List<DailyPercentageOfRequestsHandledByAIChartData> getPlotPercentageOfRequestsHandledByAIChart() {
        return adminStatsService.getPlotPercentageOfRequestsHandledByAIChart();
    }

    @Override
    @GetMapping("/chart/ai-processed-request-percentage")
    public List<AIProcessedRequestPercentageChart> getAIProcessedRequestPercentageChart() {
        return adminStatsService.getAIProcessedRequestPercentageChart();
    }
}
