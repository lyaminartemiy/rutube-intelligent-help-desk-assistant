package org.example.auth.api;

import lombok.RequiredArgsConstructor;
import org.example.auth.AuthService;
import org.example.auth.model.dto.AuthDto;
import org.example.auth.model.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping("/signup")
    public void signup(@RequestBody UserDto userDto) {
        authService.signup(userDto.username(), userDto.password(), userDto.role(), userDto.fullName());
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authService.login(userDto.username(), userDto.password()));
    }
}
