/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.dynsers.remoteservice.sdk.sdk.sharedutils",
        "com.dynsers.remoteservice.sdk.utils",
        "com.dynsers.remoteservice.sdk.sdk.serviceconsumer",
        "com.dynsers.remoteservice.sdk.sdk.configuration",
})
@EnableConfigurationProperties(RemoteServiceRegistryProperties.class)
public class RemoteServiceConsumerConfig {
}
