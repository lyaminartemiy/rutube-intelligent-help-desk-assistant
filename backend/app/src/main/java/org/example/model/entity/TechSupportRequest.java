package org.example.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Table(name = "tech_support_requests")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TechSupportRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String title;

    @OneToOne(mappedBy = "request")
    Session session;

    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Employee> assignedEmployees;

    public enum Status {
        OPEN,
        IN_PROGRESS,
        CLOSED
    }
}
