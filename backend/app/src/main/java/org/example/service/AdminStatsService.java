package org.example.service;

import org.example.model.dto.AIProcessedRequestPercentageChart;
import org.example.model.dto.DailyPercentageOfRequestsHandledByAIChartData;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.SessionRepository;
import org.example.repository.TechSupportRequestRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminStatsService {
    private final EmployeeRepository employeeRepository;
    private final SessionRepository sessionRepository;
    private final TechSupportRequestRepository techSupportRequestRepository;

    public AdminStatsService(EmployeeRepository employeeRepository, SessionRepository sessionRepository, TechSupportRequestRepository techSupportRequestRepository) {
        this.employeeRepository = employeeRepository;
        this.sessionRepository = sessionRepository;
        this.techSupportRequestRepository = techSupportRequestRepository;
    }

    public Long getEmployeeCount() {
        return employeeRepository.count();
    }

    public Long getOnlineEmployeeCount() {
        return employeeRepository.countByOnlineIsTrue();
    }

    public Double getPercentageOfRequestsHandledByAI() {
        Long handledByAi = sessionRepository.countByStatusAndRequestNull(Session.Status.CLOSED);
        Long all = sessionRepository.countByStatus(Session.Status.CLOSED);
        if (all == 0) {
            return 0.0;
        }

        return (handledByAi.doubleValue() / all.doubleValue()) * 100;
    }

    public Double getPercentageOfRequestsHandledByEmployees() {
        Long handledByAi = sessionRepository.countByStatusAndRequestNotNull(Session.Status.CLOSED);
        Long all = sessionRepository.countByStatus(Session.Status.CLOSED);
        if (all == 0) {
            return 0.0;
        }

        return (handledByAi.doubleValue() / all.doubleValue()) * 100;
    }

    public Long getInProgressRequestsCount() {
        return sessionRepository.countByStatus(Session.Status.OPEN);
    }

    public Long getUnassignedRequestsCount() {
        return techSupportRequestRepository.countByStatus(TechSupportRequest.Status.OPEN);
    }

    public List<DailyPercentageOfRequestsHandledByAIChartData> getDailyPercentageOfRequestsHandledByAIChart() {
        List<Session> handledByAi = sessionRepository.findAllByStatusAndRequestNull(Session.Status.CLOSED);
        List<Session> all = sessionRepository.findAllByStatus(Session.Status.CLOSED);

        Map<ZonedDateTime, Long> handledByAiCount = handledByAi.stream()
                .collect(Collectors.groupingBy(session -> session.getCreatedAt().truncatedTo(ChronoUnit.DAYS), Collectors.counting()));

        Map<ZonedDateTime, Long> allCount = all.stream()
                .collect(Collectors.groupingBy(session -> session.getCreatedAt().truncatedTo(ChronoUnit.DAYS), Collectors.counting()));

        List<DailyPercentageOfRequestsHandledByAIChartData> chartData = new ArrayList<>();

        Set<ZonedDateTime> allDays = new HashSet<>(handledByAiCount.keySet());
        allDays.addAll(allCount.keySet());

        for (ZonedDateTime day : allDays) {
            long handledByAiSessions = handledByAiCount.getOrDefault(day, 0L);
            long totalSessions = allCount.getOrDefault(day, 0L);
            double percentage = totalSessions > 0 ? (double) handledByAiSessions / totalSessions * 100 : 0.0;


            DailyPercentageOfRequestsHandledByAIChartData data = new DailyPercentageOfRequestsHandledByAIChartData(percentage,
                    day);
            chartData.add(data);
        }

        return chartData;
    }

    public List<AIProcessedRequestPercentageChart> getAIProcessedRequestPercentageChart() {
        List<Session> handledByAi = sessionRepository.findAllByStatusAndRequestNull(Session.Status.CLOSED);
        List<Session> all = sessionRepository.findAllByStatus(Session.Status.CLOSED);

        ZonedDateTime startDate = handledByAi.stream()
                .map(Session::getCreatedAt)
                .min(ZonedDateTime::compareTo)
                .orElse(ZonedDateTime.now());

        Map<ZonedDateTime, Long> handledByAiCount = handledByAi.stream()
                .collect(Collectors.groupingBy(session -> session.getCreatedAt().truncatedTo(ChronoUnit.DAYS), Collectors.counting()));

        Map<ZonedDateTime, Long> allCount = all.stream()
                .collect(Collectors.groupingBy(session -> session.getCreatedAt().truncatedTo(ChronoUnit.DAYS), Collectors.counting()));

        List<AIProcessedRequestPercentageChart> chartData = new ArrayList<>();

        long totalHandledByAi = 0;
        long totalSessions = 0;

        ZonedDateTime currentDate = startDate;
        while (!currentDate.isAfter(ZonedDateTime.now())) {
            totalHandledByAi += handledByAiCount.getOrDefault(currentDate, 0L);
            totalSessions += allCount.getOrDefault(currentDate, 0L);

            double percentage = totalSessions > 0 ? (double) totalHandledByAi / totalSessions * 100 : 0.0;

            AIProcessedRequestPercentageChart data = new AIProcessedRequestPercentageChart(percentage, currentDate);
            chartData.add(data);

            currentDate = currentDate.plusDays(1);
        }

        chartData.add(0, new AIProcessedRequestPercentageChart(0.0, startDate));

        return chartData;
    }


}
