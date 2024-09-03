package org.example.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public void createNewSession(String chatId) {
        Session openedSession = sessionRepository.findByStatusAndChatId(Session.Status.OPEN, chatId);
        openedSession.setStatus(Session.Status.CLOSED);
        sessionRepository.save(
                Session.builder()
                        .chatId(chatId)
                        .status(Session.Status.OPEN)
                        .build()
        );
    }

    public void closeSession(String chatId) {
        Session openedSession = sessionRepository.findByStatusAndChatId(Session.Status.OPEN, chatId);
        openedSession.setStatus(Session.Status.CLOSED);
    }

    public Session getOpenedSessionByChatId(String chatId) {
        return sessionRepository.findByStatusAndChatId(Session.Status.OPEN, chatId);
    }
}
