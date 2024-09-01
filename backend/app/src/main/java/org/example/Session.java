package org.example;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "sessions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String chatId;

    @Enumerated(EnumType.STRING)
    Status status;

    @ManyToMany
    List<User> participants;

    public enum Status {
        OPEN,
        CLOSED
    }
}
