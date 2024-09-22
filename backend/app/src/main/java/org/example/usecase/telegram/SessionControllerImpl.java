package org.example.usecase.telegram;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class SessionControllerImpl implements SessionController {
    @Override
    @PostMapping
    public void createNewSession(@RequestParam String chatId) {

    }

    @Override
    @PostMapping("/send")
    public void sendSessionToTechSupport(@RequestParam String chatId) {

    }
}
