package org.example.repository;

import org.example.model.entity.Message;
import org.example.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select m from Message m where m.messageId = ?1")
    Optional<Message> findOneByMessageId(String messageId);

    @Query("select m from Message m where m.session = ?1 order by m.createdAt asc")
    List<Message> findBySession(Session session);
    List<Message> findBySessionId(Long sessionId);
}