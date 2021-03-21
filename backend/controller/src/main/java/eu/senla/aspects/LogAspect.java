package eu.senla.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.stream.Collectors;
import java.util.stream.Stream;
@Log4j2
@Component
@Aspect
public class LogAspect {
    @Pointcut("execution(public * eu.senla.CommunityController.*(..))")
    public void controllerMethod() {
    }

    @Before("controllerMethod()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        String args = Stream.of(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(","));
        log.info("before" + joinPoint.getSignature().getName() + ", args = " + args);
    }

    @Around("controllerMethod()")
    public Object aroundCallAt(ProceedingJoinPoint call) throws Throwable {
        StopWatch clock = new StopWatch(call.toString());
        Object returnValue = null;
        try {
            System.out.println("<<<<<<<<<<<Short>>>>>>>>>>> " + call.toString());
            System.out.println("<<<<<<<<<<<Long>>>>>>>>>>> " + call.toLongString());
            clock.start(call.toShortString());
            returnValue = call.proceed();
            return returnValue;
        } finally {
            clock.stop();
            System.out.println(clock.getLastTaskTimeMillis());
            System.out.println("Method return value:" + returnValue);

        }
    }
}
