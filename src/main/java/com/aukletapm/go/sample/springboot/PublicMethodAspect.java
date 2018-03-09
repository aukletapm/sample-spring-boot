package com.aukletapm.go.sample.springboot;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * An aspect does around all public method, Which uses the timer of Metrics to measure the method execution.
 *
 * @author Eric Xu
 * @date 01/03/2018
 */
@Aspect
@Component
public class PublicMethodAspect {
    private MetricRegistry metricRegistry = new MetricRegistry();

    public MetricRegistry getMetricRegistry() {
        return metricRegistry;
    }

    @Pointcut("execution(public * *(..))")
    private void publicMethod() {

    }

    @Pointcut("within(com.aukletapm.go.sample.springboot.*..*)")
    private void withinPackage() {
    }

    @Around("withinPackage() && publicMethod()")
    private Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String packageName = methodSignature.getDeclaringTypeName();
        Timer timer = metricRegistry.timer(String.format("%s.%s", packageName, methodSignature.getName()));
        Timer.Context context = timer.time();
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } finally {
            context.stop();
        }
    }
}
