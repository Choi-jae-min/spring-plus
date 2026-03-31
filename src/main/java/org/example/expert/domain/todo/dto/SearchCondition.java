package org.example.expert.domain.todo.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


public record SearchCondition(
        String title,
        String nickName,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createStartAt,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime createEndAt
) {
}
