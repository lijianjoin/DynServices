/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.server.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Around("within(com.dynsers.remoteservice.server.services..*)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        pjp.getSignature();
        long end = System.currentTimeMillis();
        //        logger.debug(pjp.getSignature().toShortString() + " Finish: " + (end - start) + "ms");

        return new Object();
    }

    @Before("execution(com.dynsers.remoteservice.server.services.new(..))")
    public void beforeConstructor(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("Before constructor logic for monitored class: ");
        System.out.println("Arguments passed -> " + args[0]);
    }


}
