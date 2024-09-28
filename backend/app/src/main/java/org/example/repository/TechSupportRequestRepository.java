package org.example.repository;

import org.example.model.entity.Message;
import org.example.model.entity.TechSupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface TechSupportRequestRepository extends JpaRepository<TechSupportRequest, Long> {
    Long countByStatus(TechSupportRequest.Status status);

    Long countByAssignedEmployees_UsernameAndSession_ClosedAtAfter(String username, ZonedDateTime startOfDay);

    Long countByAssignedEmployees_UsernameAndSession_ClosedAtBetween(String username, ZonedDateTime startDate, ZonedDateTime endDate);

    Long countByStatusAndAssignedEmployees_Username(TechSupportRequest.Status status, String name);

    @Query("""
            select t from TechSupportRequest t inner join t.assignedEmployees assignedEmployees
            where assignedEmployees.username = ?1""")
    List<TechSupportRequest> findByAssignedEmployees_Username(String username);

    @Query("select t from TechSupportRequest t inner join t.session.messages messages where messages.side = ?1")
    List<TechSupportRequest> findBySession_Messages_Side(Message.Side side);
}
