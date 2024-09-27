package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.EmployeeDto;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.TechSupportRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    private final TechSupportRequestRepository techSupportRequestRepository;

    public List<EmployeeDto> getAllEmployees() {
        return repository.findAll().stream()
                .map(e -> new EmployeeDto(
                        e.getId(),
                        e.getFullName(),
                        (long) e.getRequestsInProgress().size(),
                        techSupportRequestRepository.findByAssignedEmployees_Username(e.getUsername()).stream().filter(rq -> rq.getStatus().equals(TechSupportRequest.Status.CLOSED)).count(),
                        e.getOnline()
                )).toList();
    }
}
