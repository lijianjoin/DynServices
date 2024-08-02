/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.annotations;

import com.dynsers.remoteservice.sdk.enums.ServiceProviderTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceProvider {

    String id() default "";

    String serviceName();

    String version();

    String subURI() default "";

    String uuid() default "";

    int detectionInterval() default 10;

    ServiceProviderTypes type() default ServiceProviderTypes.REMOTESERVICEPROVIDER;

}
