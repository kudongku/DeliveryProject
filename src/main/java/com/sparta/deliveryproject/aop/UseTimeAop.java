package com.sparta.deliveryproject.aop;

import com.sparta.deliveryproject.entity.ApiUseTime;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.ApiUseTimeRepository;
import com.sparta.deliveryproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j(topic = "UseTimeAop")
@Aspect
@Component
@RequiredArgsConstructor
public class UseTimeAop {
    private final ApiUseTimeRepository apiUseTimeRepository;

    @Pointcut("execution(* com.sparta.deliveryproject.controller.OrderController.*(..))")
    private void orderCut() {}

    @Pointcut("execution(* com.sparta.deliveryproject.controller.ReviewController.*(..))")
    private void reviewCut() {}

    @Around("orderCut() || reviewCut()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long runTime = endTime - startTime;

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                User loginUser = userDetails.getUser();
                ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser).orElse(null);

                if (apiUseTime == null) {
                    apiUseTime = new ApiUseTime(loginUser, runTime);
                } else {
                    apiUseTime.addUseTime(runTime);
                }

                log.info("[API Use Time] Username: " + loginUser.getNickname() + ", Total Time: " + apiUseTime.getTotalTime() + " ms");
                apiUseTimeRepository.save(apiUseTime);
            }

        }

    }
}
