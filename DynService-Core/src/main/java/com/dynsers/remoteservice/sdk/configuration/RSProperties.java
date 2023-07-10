package com.dynsers.remoteservice.sdk.configuration;

import com.dynsers.remoteservice.sdk.annotations.ConfigValue;
import com.dynsers.remoteservice.sdk.parameterinject.ConfigValueParamterInject;
import lombok.Data;

@Data
public class RSProperties {

    @ConfigValue("${remoteService.serviceProvider.groupId}")
    private String serviceProviderGroupId;

    @ConfigValue("${remoteService.serviceProvider.resourceId}")
    private String serviceProviderResourceId;

    @ConfigValue("${remoteService.serviceProvider.resourceVersion}")
    private String serviceProviderResourceVersion;

    @ConfigValue("${remoteService.serviceProvider.context-path}")
    private String serviceProviderContextPath;

    @ConfigValue("${remoteService.serviceProvider.hostname}")
    private String serviceProviderHostname;

    @ConfigValue("${remoteService.server.url}")
    private String serverUrl;

    @ConfigValue("${remoteService.server.groupId}")
    private String serverGroupId;

    @ConfigValue("${remoteService.server.resourceId}")
    private String serverResourceId;

    @ConfigValue("${remoteService.server.resourceVersion}")
    private String serverResourceVersion;

    @ConfigValue("${remoteService.server.serviceVersion}")
    private String serverServiceVersion;

    private int serviceProviderPort;


    private static RSProperties instance;

    private RSProperties() {

    }

    public static RSProperties getInstance() {
        RSProperties localRef = instance;
        if (localRef == null) {
            synchronized (RSProperties.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RSProperties();
                    try {
                        ConfigValueParamterInject.injectConfigValue(instance);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return localRef;
    }

}
