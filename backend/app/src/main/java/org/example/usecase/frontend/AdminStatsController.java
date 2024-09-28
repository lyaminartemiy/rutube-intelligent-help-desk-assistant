package org.example.usecase.frontend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.AIProcessedRequestPercentageChart;
import org.example.model.dto.AdminEmployeeStats;
import org.example.model.dto.AdminRequestsStats;
import org.example.model.dto.DailyPercentageOfRequestsHandledByAIChartData;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Admin Statistics", description = "Operations related to admin statistics")
public interface AdminStatsController extends HideApiFragments {

    @Operation(summary = "Статистика сотрудников", description = "Статистика сотрудников")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успех"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    AdminEmployeeStats getAdminEmployeeStats();

    @Operation(summary = "Получить к-во сотрудников всего", description = "Возвращает общее количество сотрудников", hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество сотрудников успешно получено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getEmployeeCount();

    @Operation(summary = "Получить к-во сотрудников онлайн", description = "Возвращает количество сотрудников, находящихся онлайн", hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество онлайн сотрудников успешно получено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getOnlineEmployeeCount();

    @Operation(summary = "Статистика администратора")
    AdminRequestsStats getRequestStats();

    @Operation(summary = "Получить процент обращений обработанных ИИ", description = "Возвращает процент обращений, обработанных искусственным интеллектом", hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Процент обращений, обработанных ИИ, успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Double getPercentageOfRequestsHandledByAI();

    @Operation(summary = "Получить процент обращений обработанных сотрудниками", description = "Возвращает процент обращений, обработанных сотрудниками", hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Процент обращений, обработанных сотрудниками, успешно получен"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Double getPercentageOfRequestsHandledByEmployees();

    @Operation(summary = "Получить количество обращений в работе на текущий момент", description = "Возвращает количество обращений, находящихся в работе", hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество обращений в работе успешно получено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getInProgressRequestsCount();

    @Operation(summary = "Получить количество обращений не взятых в работу на текущий момент", description = "Возвращает количество обращений, не взятых в работу", hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество не взятых в работу обращений успешно получено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getUnassignedRequestsCount();

    @Operation(summary = "Получить процент обращений обработанных ИИ на каждый день (график)", description = "Возвращает данные для графика процента обращений, обработанных ИИ, по дням")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные для графика успешно получены"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/chart/plot-percentage-of-requests-handled-by-ai")
    List<DailyPercentageOfRequestsHandledByAIChartData> getPlotPercentageOfRequestsHandledByAIChart();

    @Operation(summary = "Получить процент обращений обработанных ИИ за всё время (график)", description = "Возвращает данные для графика процента обращений, обработанных ИИ, за всё время")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные для графика успешно получены"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    List<AIProcessedRequestPercentageChart> getAIProcessedRequestPercentageChart();
}
