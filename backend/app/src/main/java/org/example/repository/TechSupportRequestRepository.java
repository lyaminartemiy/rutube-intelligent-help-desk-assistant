package org.example.repository;

import org.example.model.entity.TechSupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TechSupportRequestRepository extends JpaRepository<TechSupportRequest, Long> {
    Long countByStatus(TechSupportRequest.Status status);

    Long countByAssignedEmployees_UsernameAndSession_ClosedAtAfter(String username, ZonedDateTime startOfDay);

    Long countByAssignedEmployees_UsernameAndSession_ClosedAtBetween(String username, ZonedDateTime startDate, ZonedDateTime endDate);

    Long countByStatusAndAssignedEmployees_Username(TechSupportRequest.Status status, String name);

    @Query("""
            select t from TechSupportRequest t inner join t.assignedEmployees assignedEmployees
            where assignedEmployees.username = ?1""")
    List<TechSupportRequest> findByAssignedEmployees_Username(String username);
}
