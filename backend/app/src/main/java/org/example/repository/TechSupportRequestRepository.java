package org.example.repository;

import org.example.model.entity.Employee;
import org.example.model.entity.TechSupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechSupportRequestRepository extends JpaRepository<TechSupportRequest, Long> {
    Long countByStatus(TechSupportRequest.Status status);
}
