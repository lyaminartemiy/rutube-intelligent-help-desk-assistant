package org.example.usecase.frontend;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.AuthDto;
import org.example.model.dto.SignInDto;
import org.example.model.dto.SignUpData;
import org.example.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/signup/send")
    public void sendSingUpDataToEmail(@RequestBody SignUpData data) {
        authService.sendSignUpDataToEmail(data.fullName(), data.role(), data.email());
    }

    @Override
    @PostMapping("/login")
    public AuthDto logInAccount(@RequestBody SignInDto signInDto) {
        return authService.logInAccount(signInDto.username(), signInDto.password());
    }
}
