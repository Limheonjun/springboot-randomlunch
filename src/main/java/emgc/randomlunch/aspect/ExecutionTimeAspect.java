package emgc.randomlunch.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

@Component
@Aspect
@Slf4j
public class ExecutionTimeAspect {

    @Around("execution(* emgc.randomlunch.api..*(..))")
    public Object measureExeTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        String methodName = signature.getName();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start(methodName);
        Object proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();

        log.info("{} 밀리초", String.valueOf(TimeUnit.MILLISECONDS.convert(stopWatch.getTotalTimeNanos(), TimeUnit.NANOSECONDS)));
        log.info(stopWatch.prettyPrint());
        return proceed;
    }
}
