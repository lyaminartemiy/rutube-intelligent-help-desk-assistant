package org.example.usecase.telegram.impl;

import lombok.RequiredArgsConstructor;
import org.example.usecase.telegram.SessionController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionControllerImpl implements SessionController {

    private final SessionService service;

    @Override
    @PostMapping("/bot")
    public void createNewSession(@RequestParam String chatId) {
        service.createNewSession(chatId);
    }

    @Override
    @PostMapping("/dp")
    public void sendSessionToTechSupport(@RequestParam String chatId) {
        service.sendSessionToTechSupport(chatId);
    }
}
