package org.example.expert.domain.log.dto;

public record LogSaveRequest (
        Long todoId,
        Long requestUserId,
        Long targetUserId,
        Boolean success,
        String message
){
}
