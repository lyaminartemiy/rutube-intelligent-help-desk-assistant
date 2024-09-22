package org.example.usecase.frontend;

import org.example.model.dto.TechSupportRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class TechSupportRequestListControllerImpl implements TechSupportRequestListController {
    @Override
    @GetMapping("/all")
    public List<TechSupportRequestDto> getAllOpenRequests() {
        return List.of();
    }

    @Override
    @GetMapping("/unassigned")
    public List<TechSupportRequestDto> getAllUnassignedRequests() {
        return List.of();
    }

    @Override
    @GetMapping("/all/search")
    public List<TechSupportRequestDto> getOpenRequestsBySearch(@RequestParam String search) {
        return List.of();
    }

    @Override
    @GetMapping("/assigned/open")
    public List<TechSupportRequestDto> getAssignedRequestsByEmployee(Principal principal) {
        return List.of();
    }

    @Override
    @GetMapping("/assigned/open/search")
    public List<TechSupportRequestDto> getAssignedRequestsByEmployeeAndSearch(Principal principal, @RequestParam String search) {
        return List.of();
    }

    @Override
    @GetMapping("/assigned/all")
    public List<TechSupportRequestDto> getAllRequestsAssignedToEmployee(Principal principal) {
        return List.of();
    }
}
