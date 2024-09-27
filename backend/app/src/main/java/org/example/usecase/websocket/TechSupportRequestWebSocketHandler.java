package org.example.usecase.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.service.TechSupportRequestListService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TechSupportRequestWebSocketHandler extends TextWebSocketHandler  {

    private final TechSupportRequestListService service;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Thread> sessionIdToThread = new HashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        session.sendMessage(new TextMessage(mapper.writeValueAsString(service.getAllOpenRequests())));
        sessionIdToThread.put(session.getId(), new Thread(() -> {
            var oldRequests = service.getAllOpenRequests();
            while (session.isOpen()) {
                try {
                    Thread.sleep(5000);
                    var newRequests = service.getAllOpenRequests();
                    if (!Arrays.deepToString(oldRequests.toArray()).equals(Arrays.deepToString(newRequests.toArray()))) {
                        oldRequests = newRequests;
                        String message = mapper.writeValueAsString(newRequests);
                        session.sendMessage(new TextMessage(message));
                    }
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
        sessionIdToThread.get(session.getId()).start();
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        try {
            sessionIdToThread.get(session.getId()).interrupt();
            sessionIdToThread.remove(session.getId());
        } catch (Exception e) {
            System.out.println("thread interrupted");
        }

    }
}
