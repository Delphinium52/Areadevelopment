package com.sparta.areadevelopment.util;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class ControllerLogger {
    @Pointcut("execution(* com.sparta.areadevelopment.controller.*.*(..))")
    public void controller() {
    }
    @Around("controller()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        String methodName = joinPoint.getSignature().getName();
        Map<String, Object> param = new HashMap<>();

        try{
            param.put("methodName", methodName);
            param.put("logTime", LocalDateTime.now());
            param.put("requestURL", request.getRequestURL());
            param.put("HTTP_METHOD", request.getMethod());
        }catch (Exception e){
            log.error("Error logging", e);
        }
        log.info("log info: {}", param);
        Object result = joinPoint.proceed();
        return result;
        }

}

