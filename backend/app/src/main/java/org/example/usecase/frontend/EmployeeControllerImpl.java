package org.example.usecase.frontend;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.EmployeeDto;
import org.example.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService service;

    @Override
    @GetMapping("/all")
    public List<EmployeeDto> getAllEmployees() {
        return service.getAllEmployees();
    }

    @Override
    @GetMapping("/search")
    public List<EmployeeDto> getEmployeeBySearch(@RequestParam String search) {
        return List.of();
    }
}
