package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.entity.UserProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileImage,Long> {
}
