package org.example.usecase.frontend;

import org.example.model.dto.EmployeeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeControllerImpl implements EmployeeController {
    @Override
    @GetMapping("/all")
    public List<EmployeeDto> getAllEmployees() {
        return List.of();
    }

    @Override
    @GetMapping("/search")
    public List<EmployeeDto> getEmployeeBySearch(@RequestParam String search) {
        return List.of();
    }
}
