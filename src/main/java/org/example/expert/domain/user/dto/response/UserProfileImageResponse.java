package org.example.expert.domain.user.dto.response;

public record UserProfileImageResponse(
        String imageUrl,
        String fileKey,
        Long userId
) {
}
