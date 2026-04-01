package org.example.expert.domain.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.dto.response.UserResponse;

import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public UserResponse findUserByCondition(String nickName) {
        return jpaQueryFactory
                .select(Projections.constructor(UserResponse.class,
                        user.id,
                        user.email
                ))
                .from(user)
                .where(user.nickname.eq(nickName))
                .fetchOne();
    }
}
