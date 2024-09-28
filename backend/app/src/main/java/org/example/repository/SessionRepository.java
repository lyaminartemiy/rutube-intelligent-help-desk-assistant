package org.example.repository;

import org.example.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("select s from Session s where s.chatId = ?1 and s.status = ?2 order by s.createdAt asc")
    List<Session> findByChatIdAndStatus(String chatId, Session.Status status);

}