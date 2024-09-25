package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.AuthDto;
import org.example.model.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.example.security.EmployeeUserDetailsService;
import org.example.security.JwtService;
import org.example.usecase.email.EmailService;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    private final JwtService jwtService;
    private final EmployeeUserDetailsService employeeUserDetailsService;
    private final EmployeeRepository employeeRepository;

    public AuthDto logInAccount(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        UserDetails userDetails = employeeUserDetailsService.loadUserByUsername(username);
        employeeRepository.findByUsername(username).get().setOnline(true);
        return new AuthDto(
                jwtService.generateToken(userDetails),
                Employee.Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority())
        );
    }


    public void sendSignUpDataToEmail(String fullName, Employee.Role role, String email) {
        List<Employee> employees = employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        String username = "1963";
        if (!employees.isEmpty()) {
            username = String.valueOf(employees.getFirst().getId() + 1 + 1963);
        }
        String userPassword = UUID.randomUUID().toString();
        employeeRepository.save(
                Employee.builder()
                        .username(username)
                        .encodedPassword(passwordEncoder.encode(userPassword))
                        .email(email)
                        .fullName(fullName)
                        .role(role)
                        .online(false)
                        .build()
        );
        emailService.sendEmail(email, "Регистрация пользователя " + fullName, """
                Вы были зарегистрированы на платформе технической поддержки Rutube.
                Ваши данные для входа:
                Логин: %s
                Пароль: %s
                
                Пожалуйста, сохраните это письмо, чтобы не потерять данные для входа
                """.formatted(username, userPassword));
    }
}
