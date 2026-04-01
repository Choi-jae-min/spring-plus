package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.dto.response.UserResponse;


public interface UserQueryRepository {
    UserResponse findUserByCondition(String nickName);
}
