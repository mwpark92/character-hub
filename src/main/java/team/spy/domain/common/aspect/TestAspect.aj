package team.spy.domain.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {

    @Around("@annotation(AnnotationTest)")
    public Object test(ProceedingJoinPoint joinPoint) throws Throwable
    {

        Object obect = joinPoint.proceed();

        return obect;
    }
}
