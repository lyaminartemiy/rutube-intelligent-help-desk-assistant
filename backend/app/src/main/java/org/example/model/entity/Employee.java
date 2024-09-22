package org.example.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Table(name = "employees")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String fullName;

    @Enumerated(EnumType.STRING)
    Role role;

    String username;

    String encodedPassword;

    String email;

    @ManyToMany
    List<TechSupportRequest> requestsInProgress;

    public enum Role {
        ADMIN,
        TECH_SUPPORT_EMPLOYEE
    }
}
