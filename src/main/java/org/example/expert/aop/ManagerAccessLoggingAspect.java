package org.example.expert.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.log.dto.LogSaveRequest;
import org.example.expert.domain.log.service.LogService;
import org.example.expert.domain.manager.dto.request.ManagerSaveRequest;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ManagerAccessLoggingAspect {

    private final LogService logService;

    @Around("execution(* org.example.expert.domain.manager.service.ManagerService.saveManager(..))")
    public Object logManagerSave(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        AuthUser authUser = (AuthUser) args[0];
        Long todoId = (Long) args[1];
        ManagerSaveRequest request = (ManagerSaveRequest) args[2];

        try {
            Object result = joinPoint.proceed();

            logService.saveLog(new LogSaveRequest(
                    todoId, authUser.getId(), request.getManagerUserId(),
                    true, "매니저가 성공적으로 등록되었습니다."
            ));
            return result;

        } catch (Exception e) {
            logService.saveLog(new LogSaveRequest(
                    todoId, authUser.getId(), request.getManagerUserId(),
                    false, e.getMessage()
            ));
            throw e;
        }
    }
}
