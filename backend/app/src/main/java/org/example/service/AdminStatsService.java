package org.example.service;

import org.example.model.dto.AIProcessedRequestPercentageChart;
import org.example.model.dto.DailyPercentageOfRequestsHandledByAIChartData;
import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.MessageRepository;
import org.example.repository.SessionRepository;
import org.example.repository.TechSupportRequestRepository;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AdminStatsService {
    private final EmployeeRepository employeeRepository;
    private final SessionRepository sessionRepository;
    private final TechSupportRequestRepository techSupportRequestRepository;
    private final MessageRepository messageRepository;

    public AdminStatsService(EmployeeRepository employeeRepository, SessionRepository sessionRepository, TechSupportRequestRepository techSupportRequestRepository, MessageRepository messageRepository) {
        this.employeeRepository = employeeRepository;
        this.sessionRepository = sessionRepository;
        this.techSupportRequestRepository = techSupportRequestRepository;
        this.messageRepository = messageRepository;
    }

    public Long getEmployeeCount() {
        return employeeRepository.count();
    }

    public Long getOnlineEmployeeCount() {
        return employeeRepository.countByOnlineIsTrue();
    }

    public Double getPercentageOfRequestsHandledByAI() {
        Long handledByAi = messageRepository.countBySide(Message.Side.BOT);
        Long all = messageRepository.countBySide(Message.Side.BOT) + messageRepository.countBySide(Message.Side.TECH_SUPPORT_EMPLOYEE);
        if (all == 0) {
            return 0.0;
        }

        return (handledByAi.doubleValue() / all.doubleValue()) * 100;
    }

    public Double getPercentageOfRequestsHandledByEmployees() {
        Long handledByEmployee = messageRepository.countBySide(Message.Side.TECH_SUPPORT_EMPLOYEE);
        Long all = messageRepository.countBySide(Message.Side.BOT) + messageRepository.countBySide(Message.Side.TECH_SUPPORT_EMPLOYEE);
        if (all == 0) {
            return 0.0;
        }

        return (handledByEmployee.doubleValue() / all.doubleValue()) * 100;
    }

    public Long getInProgressRequestsCount() {
        return techSupportRequestRepository.countByStatus(TechSupportRequest.Status.IN_PROGRESS);
    }

    public Long getUnassignedRequestsCount() {
        return techSupportRequestRepository.countByStatus(TechSupportRequest.Status.OPEN);
    }

    public List<DailyPercentageOfRequestsHandledByAIChartData> getPlotPercentageOfRequestsHandledByAIChart() {
        List<Message> handledByAi = messageRepository.findAllBySide(Message.Side.BOT);
        List<Message> all = messageRepository.findAllBySide(Message.Side.BOT);
        all.addAll(messageRepository.findAllBySide(Message.Side.TECH_SUPPORT_EMPLOYEE));

        Collector<Message, ?, Map<ZonedDateTime, Long>> grouping = Collectors.groupingBy(message -> message.getCreatedAt().truncatedTo(ChronoUnit.MINUTES), Collectors.counting());

        Map<ZonedDateTime, Long> handledByAiCount = handledByAi.stream()
                .collect(grouping);
        Map<ZonedDateTime, Long> allCount = all.stream()
                .collect(grouping);

        List<DailyPercentageOfRequestsHandledByAIChartData> chartData = new ArrayList<>();

        Set<ZonedDateTime> allDates = new HashSet<>(handledByAiCount.keySet());
        allDates.addAll(allCount.keySet());

        for (ZonedDateTime date : allDates) {
            long handledByAiSessions = handledByAiCount.getOrDefault(date, 0L);
            long totalSessions = allCount.getOrDefault(date, 0L);
            double percentage = totalSessions > 0 ? (double) handledByAiSessions / totalSessions * 100 : 0.0;


            DailyPercentageOfRequestsHandledByAIChartData data = new DailyPercentageOfRequestsHandledByAIChartData(percentage,
                    date);
            chartData.add(data);
        }

        return chartData;
    }

    public List<AIProcessedRequestPercentageChart> getAIProcessedRequestPercentageChart() {
        List<TechSupportRequest> handledByAi = techSupportRequestRepository.findBySession_Messages_Side(Message.Side.BOT);
        List<TechSupportRequest> all = techSupportRequestRepository.findBySession_Messages_Side(Message.Side.BOT);
        all.addAll(techSupportRequestRepository.findBySession_Messages_Side(Message.Side.TECH_SUPPORT_EMPLOYEE));

        ZonedDateTime startDate = handledByAi.stream()
                .map(TechSupportRequest::getSession)
                .map(Session::getCreatedAt)
                .min(ZonedDateTime::compareTo)
                .orElse(ZonedDateTime.now());

        Collector<Session, ?, Map<ZonedDateTime, Long>> grouping = Collectors.groupingBy(session -> session.getCreatedAt().truncatedTo(ChronoUnit.MINUTES), Collectors.counting());

        Map<ZonedDateTime, Long> handledByAiCount = handledByAi.stream().map(TechSupportRequest::getSession)
                .collect(grouping);
        Map<ZonedDateTime, Long> allCount = all.stream().map(TechSupportRequest::getSession)
                .collect(grouping);

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
        return chartData;
    }
}
