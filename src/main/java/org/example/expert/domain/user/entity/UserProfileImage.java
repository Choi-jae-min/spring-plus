package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Entity
@Table(name = "profile_images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileImage extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileKey;

    @Column(unique = true, nullable = false)
    private Long userId;

    public UserProfileImage(String fileKey, Long userId) {
        this.fileKey = fileKey;
        this.userId = userId;
    }
}
