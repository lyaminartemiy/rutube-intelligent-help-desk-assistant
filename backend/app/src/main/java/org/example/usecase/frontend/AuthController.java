package org.example.usecase.frontend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.model.dto.AuthDto;
import org.example.model.dto.SignInDto;
import org.example.model.dto.SignUpData;

@Tag(name = "Authentication", description = "Operations related to user authentication")
public interface AuthController {

    @Operation(summary = "Отправить сотруднику регистрационные данные",
            description = "Отправляет регистрационные данные на указанный email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрационные данные успешно отправлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для регистрации"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    void sendSingUpDataToEmail(
            @Parameter(description = "Данные для регистрации", required = true) @RequestBody SignUpData data
    );

    @Operation(summary = "Залогиниться в аккаунт",
            description = "Авторизует пользователя в системе и возвращает токен доступа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход в аккаунт",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthDto.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    AuthDto logInAccount(
            @Parameter(description = "Данные для входа", required = true) @RequestBody SignInDto signInDto
    );
}
