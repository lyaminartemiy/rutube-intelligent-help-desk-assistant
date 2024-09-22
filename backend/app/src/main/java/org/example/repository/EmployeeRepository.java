package org.example.repository;

import org.example.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Long countByOnlineIsTrue();
}