package com.dynsers.remoteservice.sdk.sdk.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.dynsers.remoteservice.sdk.sdk",
        "com.dynsers.remoteservice.sdk.utils"})
@EnableConfigurationProperties(RemoteServiceRegistryProperties.class)
public class RemoteServiceProviderConfig {
 
}
