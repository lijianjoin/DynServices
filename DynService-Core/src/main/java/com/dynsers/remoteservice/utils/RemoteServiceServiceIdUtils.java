/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.dynsers.remoteservice.utils;

import com.dynsers.DynService.core.api.annotation.RemoteService;
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