package org.example.usecase.frontend;

import org.example.model.dto.FileDto;
import org.example.model.dto.ProfilePictureDto;
import org.example.model.dto.UserProfileDto;
import org.example.service.EmployeeProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/employee-profiles")
public class EmployeeProfileControllerImpl implements EmployeeProfileController {
    private final EmployeeProfileService employeeProfileService;

    public EmployeeProfileControllerImpl(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @Override
    @GetMapping
    public UserProfileDto getEmployeeProfile(Principal principal) {
        return employeeProfileService.getEmployeeProfile(principal);
    }

    @Override
    @PostMapping("/picture/upload")
    public FileDto uploadEmployeeProfilePicture(Principal principal, @RequestParam("file") MultipartFile file) {
        return employeeProfileService.uploadEmployeeProfilePicture(principal, file);
    }

    @Override
    @GetMapping("/picture/download")
    public ProfilePictureDto downloadEmployeeProfilePicture(Principal principal) {
        return  employeeProfileService.downloadEmployeeProfilePicture(principal);
    }
}
