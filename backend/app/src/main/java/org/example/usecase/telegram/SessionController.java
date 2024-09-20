package org.example.usecase.telegram;

public interface SessionController {

    void createNewSession(String chatId);

    void sendSessionToTechSupport(String chatId);
}
