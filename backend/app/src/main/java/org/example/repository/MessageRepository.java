package org.example.repository;

import org.example.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select m from Message m where m.messageId = ?1")
    Optional<Message> findOneByMessageId(String messageId);

    List<Message> findAllBySide(Message.Side side);

    List<Message> findAllBySession_Id(Long sessionId);

    long countBySide(Message.Side side);
}