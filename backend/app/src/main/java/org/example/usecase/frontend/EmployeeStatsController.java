package org.example.usecase.frontend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.EmployeeStats;

import java.security.Principal;
import java.time.ZonedDateTime;

@Tag(name = "Employee Statistics", description = "Operations related to employee statistics")
public interface EmployeeStatsController extends HideApiFragments {

    @Operation(summary = "Отчет по обработанным сотрудником обращениям",
            description = "Возвращает отчет по обработанным сотрудником обращениям")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Отчет по сотруднику получен успешно"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    EmployeeStats getEmployeeStats(Principal principal);

    @Operation(summary = "Получить количество обращений, обработанных сотрудником всего",
            description = "Возвращает общее количество обращений, обработанных текущим сотрудником",
            hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество обращений успешно получено"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getTotalRequestsHandledByEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );

    @Operation(summary = "Получить количество обращений в работе по сотруднику",
            description = "Возвращает количество обращений, находящихся в работе у текущего сотрудника",
            hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество обращений в работе успешно получено"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getInProgressRequestsCountByEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );

    @Operation(summary = "Получить количество закрытых обращений сегодня по сотруднику",
            description = "Возвращает количество закрытых обращений текущего сотрудника за сегодня",
            hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество закрытых обращений сегодня успешно получено"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getClosedRequestsCountTodayByEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );

    @Operation(summary = "Получить количество закрытых обращений на этой неделе по сотруднику",
            description = "Возвращает количество закрытых обращений текущего сотрудника за эту неделю",
            hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество закрытых обращений на этой неделе успешно получено"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getClosedRequestsCountThisWeekByEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );

    @Operation(summary = "Получить количество закрытых обращений в этом месяце по сотруднику",
            description = "Возвращает количество закрытых обращений текущего сотрудника за этот месяц",
            hidden = hide_fragments)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество закрытых обращений в этом месяце успешно получено"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getClosedRequestsCountThisMonthByEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal
    );

    @Operation(summary = "Получить количество закрытых обращений за произвольную дату по сотруднику",
            description = "Возвращает количество закрытых обращений текущего сотрудника за указанный период")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество закрытых обращений за указанный период успешно получено"),
            @ApiResponse(responseCode = "404", description = "Сотрудник не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    Long getClosedRequestsCountByDateForEmployee(
            @Parameter(description = "Информация о текущем пользователе", required = true) Principal principal,
            @Parameter(description = "Дата начала", required = true) ZonedDateTime startDate,
            @Parameter(description = "Дата окончания", required = true) ZonedDateTime endDate
    );
}

