/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.annotations.RemoteService;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver;

public class RemoteServiceServiceIdUtils {

    private RemoteServiceServiceIdUtils() {}

    public static RemoteServiceId fromAnnotation(RemoteService annotation) {
        var id = new RemoteServiceId();
        id.setGroupId(RemoteServicePropertyResolver.getPropertyValue(annotation.groupId()))
                .setResourceId(RemoteServicePropertyResolver.getPropertyValue(annotation.resourceId()))
                .setResourceVersion(RemoteServicePropertyResolver.getPropertyValue(annotation.resourceVersion()))
                .setServiceVersion(RemoteServicePropertyResolver.getPropertyValue(annotation.serviceVersion()))
                .setServiceName(RemoteServicePropertyResolver.getPropertyValue(annotation.serviceName()))
                .setServiceId(RemoteServicePropertyResolver.getPropertyValue(annotation.serviceId()))
                .setServiceLocation(RemoteServicePropertyResolver.getPropertyValue(annotation.serviceLocation()))
                .setUri(RemoteServicePropertyResolver.getPropertyValue(annotation.url()))
                .setUuid(RemoteServicePropertyResolver.getPropertyValue(annotation.uuid()));
        return id;
    }

    public static String getServiceIdAsPlainString(RemoteServiceId serviceId) {
        return String.format("Group ID: %s, ", serviceId.getGroupId())
                + String.format("Resource ID: %s, ", serviceId.getResourceId())
                + String.format("Resource Version: %s, ", serviceId.getResourceVersion())
                + String.format("Service ID: %s, ", serviceId.getServiceId())
                + String.format("Service Version: %s, ", serviceId.getServiceVersion())
                + String.format("Service Name: %s, ", serviceId.getServiceName())
                + String.format("UUID: %s, ", serviceId.getUuid())
                + String.format("URI: %s, ", serviceId.getUri());
    }

    public static String getGroupResourceKey(RemoteServiceId serviceId) {
        return serviceId.getGroupId() + "_" + serviceId.getResourceId() + "_" + serviceId.getResourceVersion();
    }

    public static String getServiceKey(RemoteServiceId serviceId) {
        return serviceId.getServiceId() + "_"
                + serviceId.getServiceName()
                + "_"
                + serviceId.getServiceVersion()
                + "_"
                + serviceId.getServiceLocation();
    }
}