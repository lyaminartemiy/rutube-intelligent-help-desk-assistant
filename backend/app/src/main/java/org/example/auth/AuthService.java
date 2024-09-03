package org.example.auth;

import lombok.RequiredArgsConstructor;
import org.example.User;
import org.example.UserService;
import org.example.auth.model.dto.AuthDto;
import org.example.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthDto login(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        UserDetails userDetails = userService.loadUserByUsername(username);
        String token = jwtService.generateJwt(new HashMap<>(), userDetails);
        return new AuthDto(
                token,
                userDetails.getAuthorities().iterator().next()
        );
    }

    public void signup(String username, String password, User.Role role, String fullName) {
        userService.createUser(
                User.builder()
                        .username(username)
                        .encodedPassword(passwordEncoder.encode(password))
                        .fullName(fullName)
                        .shortName(fullName.split(" ")[1])
                        .role(role)
                        .build()
        );
    }
}
