package org.example.model.dto;

import java.time.ZonedDateTime;

public record DailyPercentageOfRequestsHandledByAIChartData(
        Double percentage,
        ZonedDateTime dateTime
) {
}
