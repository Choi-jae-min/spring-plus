package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long todoId;
    private Long requestUserId;
    private Long targetUserId;

    private boolean success;
    private String message;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    public Log(Long todoId, Long requestUserId, Long targetUserId, boolean success, String message) {
        this.todoId = todoId;
        this.requestUserId = requestUserId;
        this.targetUserId = targetUserId;
        this.success = success;
        this.message = message;
    }

}
