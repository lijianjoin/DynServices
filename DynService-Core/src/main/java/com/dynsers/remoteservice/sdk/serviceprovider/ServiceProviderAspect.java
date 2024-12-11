package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.annotations.ServiceProvider;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceProviderAspect {

    @Before("(execution(*.new(..)) || execution(*.new())) && @target(com.dynsers.remoteservice.annotations.ServiceProvider)")
    public void beforeConstructor(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("Before constructor logic for monitored class: ");
        System.out.println("Arguments passed -> " + args[0]);
    }



}