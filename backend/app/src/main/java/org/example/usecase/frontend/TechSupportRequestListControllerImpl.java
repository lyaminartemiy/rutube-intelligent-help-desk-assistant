package org.example.usecase.frontend;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.TechSupportRequestDto;
import org.example.service.TechSupportRequestListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class TechSupportRequestListControllerImpl implements TechSupportRequestListController {

    private final TechSupportRequestListService service;

    @Override
    @GetMapping("/all")
    public List<TechSupportRequestDto> getAllOpenRequests() {
        return service.getAllOpenRequests();
    }

    @Override
    @GetMapping("/unassigned")
    public List<TechSupportRequestDto> getAllUnassignedRequests() {
        return service.getAllUnassignedRequests();
    }

    @Override
    @GetMapping("/all/search")
    public List<TechSupportRequestDto> getOpenRequestsBySearch(@RequestParam String search) {
        return List.of();
    }

    @Override
    @GetMapping("/assigned/open")
    public List<TechSupportRequestDto> getAssignedRequestsByEmployee(Principal principal) {
        return service.getAssignedRequestsByEmployee(principal.getName());
    }

    @Override
    @GetMapping("/assigned/open/search")
    public List<TechSupportRequestDto> getAssignedRequestsByEmployeeAndSearch(Principal principal, @RequestParam String search) {
        return List.of();
    }

    @Override
    @GetMapping("/assigned/all")
    public List<TechSupportRequestDto> getAllRequestsAssignedToEmployee(Principal principal) {
        return service.getAllRequestsAssignedToEmployee(principal.getName());
    }
}
