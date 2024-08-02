package com.dynsers.remoteservice.sdk.sdk.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration("rsServiceProviderProperties")
@ConfigurationProperties(prefix = "remote-service.service-provider")
@Data
public class RemoteServiceProviderProperties {

    private String groupId;
    private String resourceId;
    private String resourceVersion;
    private String contextPath;
    private String hostname;
}
