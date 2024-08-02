/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.annotations;


import com.dynsers.remoteservice.sdk.enums.RemoteServiceInitialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteService {
    String groupId() default "";

    String resourceId() default "";

    String resourceVersion() default "";

    String serviceId() default "";

    String serviceVersion() default "";

    String serviceName() default "";

    String uuid() default "";

    String url() default "";

    RemoteServiceInitialization initialization() default RemoteServiceInitialization.SPRINGBEANINIT;
}
