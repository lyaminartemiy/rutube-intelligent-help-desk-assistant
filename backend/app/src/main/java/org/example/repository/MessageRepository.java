package org.example.repository;

import org.example.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySessionId(Long sessionId);
    List<Message> findBySession_Request_Id(Long requestId);
}
