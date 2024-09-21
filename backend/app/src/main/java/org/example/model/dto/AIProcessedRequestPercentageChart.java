package org.example.model.dto;

import java.time.ZonedDateTime;

public record AIProcessedRequestPercentageChart(
        Double percentage,
        ZonedDateTime dateTime
) {
}
