package com.dynsers.remoteservice.sdk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteService {
    String alias() default "";
    String groupId() default "";
    String resourceId() default "";
    String resourceVersion() default "";
    String serviceId() default "";
    String serviceVersion() default "";
    String url() default "";
}
