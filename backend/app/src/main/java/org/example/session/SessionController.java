//package org.example.session;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import org.springframework.http.ProblemDetail;
//
//public interface SessionController {
//
//    /**
//     * Сначала закрывает все предыдущие сессии по chatId, затем создает новый объект сессии и сохраняет в базу данных
//     *
//     * @param dto данные для создания сессии
//     */
//    @Operation(summary = "Creates a new session",
//            description = "Closes all previous sessions by chatId, then creates a new session object and saves it to the database.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Session created successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid input",
//                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
//            @ApiResponse(responseCode = "500", description = "Internal server error",
//                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
//    })
//    void createNewSession(
//            @RequestBody(description = "Data to create a new session", required = true,
//                    content = @Content(schema = @Schema(implementation = SessionInfoDto.class)))
//            SessionInfoDto dto
//    );
//
//    /**
//     * Закрывает сессию пользователя по chatId
//     *
//     * @param dto данные для создания сессии
//     */
//    @Operation(summary = "Closes selected session",
//            description = "Closes user session based on chatId")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Session closed successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid input",
//                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
//            @ApiResponse(responseCode = "500", description = "Internal server error",
//                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
//    })
//    void closeSession(@RequestBody(description = "Data to close session", required = true,
//            content = @Content(schema = @Schema(implementation = SessionInfoDto.class)))
//                      SessionInfoDto dto);
//}
