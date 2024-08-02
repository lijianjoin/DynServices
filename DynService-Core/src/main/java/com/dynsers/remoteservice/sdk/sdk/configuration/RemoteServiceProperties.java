/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class RemoteServiceProperties {

    private static RemoteServiceProperties instance;
    @Value("${remoteService.serviceProvider.groupId}")
    private String serviceProviderGroupId;
    @Value("${remoteService.serviceProvider.resourceId}")
    private String serviceProviderResourceId;
    @Value("${remoteService.serviceProvider.resourceVersion}")
    private String serviceProviderResourceVersion;
    @Value("${remoteService.serviceProvider.context-path}")
    private String serviceProviderContextPath;
    @Value("${remoteService.serviceProvider.hostname}")
    private String serviceProviderHostname;
    @Value("${remoteService.server.url}")
    private String serverUrl;
    @Value("${remoteService.server.groupId}")
    private String serverGroupId;
    @Value("${remoteService.server.resourceId}")
    private String serverResourceId;
    @Value("${remoteService.server.resourceVersion}")
    private String serverResourceVersion;
    @Value("${remoteService.server.serviceVersion}")
    private String serverServiceVersion;
    private Integer serviceProviderPort;

    private RemoteServiceProperties() {

    }

    public static RemoteServiceProperties getInstance() {
        RemoteServiceProperties localRef = instance;
        if (localRef == null) {
            synchronized (RemoteServiceProperties.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RemoteServiceProperties();
                }
            }
        }
        return localRef;
    }

}
