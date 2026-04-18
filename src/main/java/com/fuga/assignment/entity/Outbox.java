package com.fuga.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutboxStatus status = OutboxStatus.CREATED;

    @Column(name = "album_id", nullable = false)
    private Long albumId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public enum OutboxType {
        ALBUM_CREATED,
        ALBUM_VALIDATED,
        ALBUM_PUBLISHED,
    }

    public enum OutboxStatus {
        CREATED,
        SENT
    }
}
