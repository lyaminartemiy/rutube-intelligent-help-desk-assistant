package org.example.service;

import org.example.model.dto.FileDto;
import org.example.model.dto.ProfilePictureDto;
import org.example.model.dto.UserProfileDto;
import org.example.model.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Service
public class EmployeeProfileService {

    private final EmployeeRepository employeeRepository;
    private final  MinioService  minioService;

    public EmployeeProfileService(EmployeeRepository employeeRepository, MinioService minioService) {
        this.employeeRepository = employeeRepository;
        this.minioService = minioService;
    }

    public UserProfileDto getEmployeeProfile(Principal principal) {
        Employee employee = employeeRepository.getEmployeesByUsername(principal.getName());
        return new UserProfileDto(employee.getFullName(),employee.getRole().name(), employee.getEmail());
    }

    public FileDto uploadEmployeeProfilePicture(Principal principal, MultipartFile file) {
//        Employee employee = employeeRepository.getEmployeesByUsername(principal.getName());
        FileDto fileDto = minioService.uploadFile(file);
//        employee.setProfilePicS3Id(fileDto.getS3UUID());
//        employeeRepository.save(employee);
        return fileDto;
    }

    public ProfilePictureDto downloadEmployeeProfilePicture(Principal principal) {
        Employee employee = employeeRepository.getEmployeesByUsername(principal.getName());
        return new ProfilePictureDto(employee.getProfilePicS3Id(),minioService.getFileAsByteArray(employee.getProfilePicS3Id()));
    }
}
