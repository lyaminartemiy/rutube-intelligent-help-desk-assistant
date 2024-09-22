package org.example.repository;

import org.example.model.entity.TechSupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;

public interface TechSupportRequestRepository extends JpaRepository<TechSupportRequest, Long> {
    Long countByStatus(TechSupportRequest.Status status);

    Long countByAssignedEmployees_UsernameAndSession_ClosedAtAfter(String username, ZonedDateTime startOfDay);

    Long countByAssignedEmployees_UsernameAndSession_ClosedAtBetween(String username, ZonedDateTime startDate, ZonedDateTime endDate);

    Long countByStatusAndAssignedEmployees_Username(TechSupportRequest.Status status, String name);
}
