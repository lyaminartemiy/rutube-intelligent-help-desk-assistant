package org.example.message;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "additional_contents")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class AdditionalContent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    UUID fileStorageLink;

    String fileExtension;

    @Enumerated(EnumType.STRING)
    Type type;

    @ManyToOne
    @JoinColumn(name = "message_id")
    Message message;

    public enum Type {
        AUDIO,
        VIDEO,
        OTHER // Файлы такого формата просто превращаем в их расширение и как файл отправляем, чтоб мозга не парить
    }
}
