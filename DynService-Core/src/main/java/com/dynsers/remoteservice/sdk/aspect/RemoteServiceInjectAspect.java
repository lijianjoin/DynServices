
/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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