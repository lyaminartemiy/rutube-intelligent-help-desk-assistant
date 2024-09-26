package org.example.usecase.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.MessageDto;
import org.example.service.MessageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DialogueWebSocketHandler extends TextWebSocketHandler {

    private final MessageService messageService;

    static class RequestIdWithThread {
        public Long requestId;
        public Thread thread;
    }

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, RequestIdWithThread> sessionIdToRequestIdWithThread = new HashMap<>();
    private final Map<String, Thread> sessionIdToMainThread = new HashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        Objects.requireNonNull(sessionIdToMainThread.put(session.getId(), new Thread(() -> {
            while (!sessionIdToRequestIdWithThread.containsKey(session.getId())) {
            }
            RequestIdWithThread requestIdWithThread = sessionIdToRequestIdWithThread.get(session.getId());
            requestIdWithThread.thread.start();
        }))).start();
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        RequestIdWithThread rqwt = new RequestIdWithThread();
        rqwt.requestId = Long.valueOf(message.getPayload());
        rqwt.thread = new Thread(() -> {
            List<MessageDto> messages = messageService.getMessagesByRequestId(rqwt.requestId);
            try {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(messages)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        RequestIdWithThread rqwt = sessionIdToRequestIdWithThread.get(session.getId());
        if (rqwt != null) {
            rqwt.thread.interrupt();
            sessionIdToRequestIdWithThread.remove(session.getId());
        }
        sessionIdToMainThread.get(session.getId()).interrupt();
        sessionIdToMainThread.remove(session.getId());
    }
}
