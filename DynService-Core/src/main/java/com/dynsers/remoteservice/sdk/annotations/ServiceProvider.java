package com.dynsers.remoteservice.sdk.annotations;

import com.dynsers.remoteservice.sdk.enums.ServerProviderTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceProvider {

    String name() default "";
    String version();
    String subURI() default "";

    String uuid() default "";

    ServerProviderTypes type() default ServerProviderTypes.REMOTESERVICEPROVIDER;
}
