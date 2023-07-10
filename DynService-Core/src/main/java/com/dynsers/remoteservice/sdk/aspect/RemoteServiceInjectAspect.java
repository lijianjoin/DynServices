package com.dynsers.remoteservice.sdk.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
//@Aspect
public class RemoteServiceInjectAspect {

    //@Pointcut("@annotation(com.dynsers.dynservice.sdk.annotations.RemoteService)")
    public void remoteServiceInjectPointcut(){
    }

    //@Pointcut("@within(com.dynsers.dynservice.sdk.annotations.RemoteService)")
    void methodOfAnnotatedClass2() {}

    //@Before("methodOfAnnotatedClass2()")
    public void logAllMethodCallsAdvice2(){
        System.out.println("Testing......2");
    }


}