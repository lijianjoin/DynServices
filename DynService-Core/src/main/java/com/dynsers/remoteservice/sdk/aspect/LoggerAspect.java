package com.dynsers.remoteservice.sdk.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
//@Aspect
public class LoggerAspect {

    //@Pointcut("@annotation(com.dynsers.dynservice.sdk.annotations.Logger)")
    public void logPointcut(){
    }

    //@Before("logPointcut()")
    public void logAllMethodCallsAdvice(){
        System.out.println("In Aspect it is record");
    }
}
