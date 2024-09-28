package org.example.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Table(name = "messages")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String messageId;

    @Column(columnDefinition = "TEXT")
    String messageText;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    ZonedDateTime createdAt;

    @Enumerated(EnumType.STRING)
    Side side;

    Boolean isHelpful;

    @ManyToOne
    @JoinColumn(name = "session_id")
    Session session;
    
    String author;

    public enum Side {
        USER,
        BOT,
        TECH_SUPPORT_EMPLOYEE
    }

}
