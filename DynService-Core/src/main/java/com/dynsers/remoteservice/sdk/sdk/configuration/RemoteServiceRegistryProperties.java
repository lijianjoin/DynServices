package com.dynsers.remoteservice.sdk.sdk.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("rsRegistryProperties")
@ConfigurationProperties(prefix = "remote-service.server")
@Data
public class RemoteServiceRegistryProperties {
    private String groupId;
    private String resourceId;
    private String resourceVersion;
    private String serviceVersion;
    private String url;
}
