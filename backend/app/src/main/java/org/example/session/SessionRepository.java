package org.example.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("select s from Session s where s.status = ?1 and s.chatId = ?2")
    Session findByStatusAndChatId(Session.Status status, String chatId);
}