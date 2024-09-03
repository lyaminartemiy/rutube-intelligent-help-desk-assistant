package org.example.session;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionControllerImpl implements SessionController {

    private final SessionService sessionService;

    @Override
    @PostMapping
    public void createNewSession(@RequestBody SessionInfoDto dto) {
        sessionService.createNewSession(dto.chatId());
    }

    @Override
    @PostMapping("/close")
    public void closeSession(SessionInfoDto dto) {
        sessionService.closeSession(dto.chatId());
    }
}
