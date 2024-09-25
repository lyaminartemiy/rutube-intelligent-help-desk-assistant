package org.example.repository;

import org.example.model.entity.Employee;
import org.example.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("select s from Session s where s.chatId = ?1 and s.status = ?2 order by s.createdAt asc")
    List<Session> findByChatIdAndStatus(String chatId, Session.Status status);

    @Query("select m.session from Message m where m.messageId = ?1")
    Session findOneByMessageId(String messageId);
    Long countByStatusAndRequestNotNull(Session.Status status);

    Long countByStatus(Session.Status status);

    Long countByStatusAndRequestNull(Session.Status status);

    List<Session> findAllByStatusAndRequestNull(Session.Status status);

    List<Session> findAllByStatus(Session.Status status);
}