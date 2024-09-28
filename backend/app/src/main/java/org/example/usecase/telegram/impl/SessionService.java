package org.example.usecase.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.entity.Session;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.SessionRepository;
import org.example.repository.TechSupportRequestRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionService {

    private final SessionRepository sessionRepository;
    private final TechSupportRequestRepository techSupportRequestRepository;

    public void createNewSession(String chatId) {
        // Закрыть все предыдущие открытые сессии пользователя (должна быть всего одна)
        log.info("Закрыть все предыдущие открытые сессии пользователя (должна быть всего одна)");
        sessionRepository.findByChatIdAndStatus(chatId, Session.Status.OPEN).forEach(session -> {
            session.setClosedAt(ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
            session.setStatus(Session.Status.CLOSED);
        });

        // Открыть новую сессию и сохранить в бд
        log.info("Открыть новую сессию и сохранить в бд");
        sessionRepository.save(
                Session.builder()
                        .chatId(chatId)
                        .status(Session.Status.OPEN)
                        .createdAt(ZonedDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
                        .requests(new ArrayList<>())
                        .build()
        );
    }

    public void sendSessionToTechSupport(String chatId) {
        // Получить текущую сессию
        Session currentOpenedSession = sessionRepository.findByChatIdAndStatus(chatId, Session.Status.OPEN).getFirst();

        // Создать запрос в тех. поддержку
        TechSupportRequest supportRequest = techSupportRequestRepository.save(
                TechSupportRequest.builder()
                        .title("Запрос в техподдержку") // TODO хотелось бы получить от ИИ
                        .session(currentOpenedSession)
                        .status(TechSupportRequest.Status.OPEN)
                        .assignedEmployees(new ArrayList<>())
                        .build()
        );
        currentOpenedSession.getRequests().add(supportRequest);
        sessionRepository.save(currentOpenedSession);
    }
}
