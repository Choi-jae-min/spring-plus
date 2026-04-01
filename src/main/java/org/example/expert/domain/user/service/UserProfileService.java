package org.example.expert.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.service.S3Service;
import org.example.expert.domain.user.dto.response.UserProfileImageResponse;
import org.example.expert.domain.user.entity.UserProfileImage;
import org.example.expert.domain.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private static final String USER_PROFILE_PATH = "user-profile";

    private final UserProfileRepository userProfileRepository;
    private final S3Service s3Service;

    @Transactional
    public UserProfileImageResponse saveProfile(MultipartFile file, Long userId) {
        String fileKey = s3Service.uploadImage(file, USER_PROFILE_PATH);

        UserProfileImage userProfileImage = new UserProfileImage(fileKey, userId);

        userProfileRepository.save(userProfileImage);

        String presignedUrl = s3Service.getImageUrl(fileKey).toString();

        return new UserProfileImageResponse(presignedUrl ,fileKey , userId);
    }
}
