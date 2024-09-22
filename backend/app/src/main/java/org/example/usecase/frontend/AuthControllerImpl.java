package org.example.usecase.frontend;

import org.example.model.dto.AuthDto;
import org.example.model.dto.SignInDto;
import org.example.model.dto.SignUpData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {
    @Override
    @PostMapping("/signup/send")
    public void sendSingUpDataToEmail(@RequestBody SignUpData data) {

    }

    @Override
    @PostMapping("/login")
    public AuthDto logInAccount(@RequestBody SignInDto signInDto) {
        return null;
    }
}
