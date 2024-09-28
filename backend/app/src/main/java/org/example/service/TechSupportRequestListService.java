package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.TechSupportRequestDto;
import org.example.model.entity.Employee;
import org.example.model.entity.TechSupportRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.TechSupportRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechSupportRequestListService {

    private final TechSupportRequestRepository repository;
    private final EmployeeRepository employeeRepository;

    public List<TechSupportRequestDto> getAllOpenRequests() {
        return repository.findAll().stream()
                .filter(request -> !request.getStatus().equals(TechSupportRequest.Status.CLOSED))
                .map(request -> new TechSupportRequestDto(
                        request.getTitle(),
                        request.getId(),
                        request.getStatus().equals(TechSupportRequest.Status.IN_PROGRESS),
                        request.getAssignedEmployees().stream().map(Employee::getFullName).toList()
                )
        ).sorted((o1, o2) -> o2.id().compareTo(o1.id())).toList();
    }

    public List<TechSupportRequestDto> getAllUnassignedRequests() {
        return repository.findAll().stream()
                .filter(request -> request.getStatus().equals(TechSupportRequest.Status.OPEN))
                .map(request -> new TechSupportRequestDto(
                                request.getTitle(),
                                request.getId(),
                        false,
                                request.getAssignedEmployees().stream().map(Employee::getFullName).toList()
                        )
                ).toList();
    }

    public List<TechSupportRequestDto> getAssignedRequestsByEmployee(String username) {
        Employee employee = employeeRepository.getEmployeesByUsername(username);

        return employee.getRequestsInProgress().stream()
                .filter(request -> request.getStatus().equals(TechSupportRequest.Status.IN_PROGRESS))
                .map(request -> new TechSupportRequestDto(
                                request.getTitle(),
                                request.getId(),
                                false,
                                request.getAssignedEmployees().stream().map(Employee::getFullName).toList()
                        )
                ).toList();
    }

    public List<TechSupportRequestDto> getAllRequestsAssignedToEmployee(String username) {
        return repository.findByAssignedEmployees_Username(username).stream()
                .map(request -> new TechSupportRequestDto(
                                request.getTitle(),
                                request.getId(),
                                false,
                                request.getAssignedEmployees().stream().map(Employee::getFullName).toList()
                        )
                ).toList();
    }
}
