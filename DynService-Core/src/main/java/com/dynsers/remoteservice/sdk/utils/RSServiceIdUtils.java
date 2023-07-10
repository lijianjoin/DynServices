package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import com.dynsers.remoteservice.sdk.configuration.RSPropertyResolver;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;

public class RSServiceIdUtils {

    public static RemoteServiceId fromAnnotation(RemoteService annotation) {
        RemoteServiceId id = new RemoteServiceId();
        id.setGroupId(RSPropertyResolver.getPropertyValue(annotation.groupId()));
        id.setResourceId(RSPropertyResolver.getPropertyValue(annotation.resourceId()));
        id.setResourceVersion(RSPropertyResolver.getPropertyValue(annotation.resourceVersion()));
        id.setServiceVersion(RSPropertyResolver.getPropertyValue(annotation.serviceVersion()));
        id.setServiceId(RSPropertyResolver.getPropertyValue(annotation.serviceId()));
        id.setUri(RSPropertyResolver.getPropertyValue(annotation.url()));
        return id;
    }

    public static String getServiceIdAsPlainString(RemoteServiceId serviceId) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Group ID: %s, ", serviceId.getGroupId()));
        sb.append(String.format("Resource ID: %s, ", serviceId.getResourceId()));
        sb.append(String.format("Resource Version: %s, ", serviceId.getResourceVersion()));
        sb.append(String.format("Service ID: %s, ", serviceId.getServiceId()));
        sb.append(String.format("Service Version: %s, ", serviceId.getServiceVersion()));
        sb.append(String.format("UUID: %s, ", serviceId.getUuid()));
        sb.append(String.format("URI: %s, ", serviceId.getUri()));
        return sb.toString();
    }

    public static String getGroupResourceKey(RemoteServiceId serviceId) {
        StringBuilder sb = new StringBuilder();
        sb.append(serviceId.getGroupId());
        sb.append("_");
        sb.append(serviceId.getResourceId());
        sb.append("_");
        sb.append(serviceId.getResourceVersion());
        return sb.toString();
    }

    public static String getServiceKey(RemoteServiceId serviceId) {
        StringBuilder sb = new StringBuilder();
        sb.append(serviceId.getServiceId());
        sb.append("_");
        sb.append(serviceId.getServiceVersion());
        return sb.toString();
    }
}
