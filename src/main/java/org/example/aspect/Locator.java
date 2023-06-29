package org.example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@EnableAspectJAutoProxy
public class Locator {
// POINTCUTS
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceAnnotated(){}

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void isServiceAnnotatedAsRepository(){}

    @Pointcut("within(org..*Service)")
    public void isServiceClassByName(){}

    @Pointcut("within(org.example.service.*)")
    public void isServiceByPackage(){}

    @Pointcut("execution(public * org.example.service.CustomerService.get(*))")
    public void isMethodByName(){}

    @Pointcut("isServiceAnnotated() || isServiceByPackage()")
    public void isServiceAnnoOrPackage(){}

    // ADVICES

    @Around(value = "isServiceAnnotated()")
    public Object annotatedServiceStartingLog(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        log.warn(">>> BEFORE SERVICE ANNOTATED {}", proceed);
        return proceed;
    }

//    @Before("isServiceAnnotated()")
//    public void annotatedServiceStartingLog(JoinPoint joinPoint){
//        log.warn(">>> BEFORE SERVICE ANNOTATED {}", joinPoint.toShortString());
//    }
//
//    @AfterThrowing("isServiceAnnotatedAsRepository()")
//    public void annotatedRepositoryErrorLog(JoinPoint joinPoint){
//        log.error(">>> Error in repo {}", joinPoint.toShortString());
//    }
//
//    @AfterReturning("isServiceAnnotatedAsRepository()")
//    public void annotatedRepositoryReturningLog(JoinPoint joinPoint){
//
//        log.error(">>> Return something in repo {}", joinPoint.toShortString());
//
//    }

}
