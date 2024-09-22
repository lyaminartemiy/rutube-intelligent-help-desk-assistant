package org.example.repository;

import org.example.model.entity.Employee;
import org.example.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Long countByStatusAndRequestNotNull(Session.Status status);

    Long countByStatus(Session.Status status);

    Long countByStatusAndRequestNull(Session.Status status);

    List<Session> findAllByStatusAndRequestNull(Session.Status status);

    List<Session> findAllByStatus(Session.Status status);
}
