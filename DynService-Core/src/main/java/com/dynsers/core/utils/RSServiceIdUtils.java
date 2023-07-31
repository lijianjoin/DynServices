/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.dynsers.core.utils;

import com.dynsers.core.annotations.RemoteService;
import com.dynsers.core.configuration.RSPropertyResolver;
import com.dynsers.core.data.RemoteServiceId;

public class RSServiceIdUtils {

    public static RemoteServiceId fromAnnotation(RemoteService annotation) {
        RemoteServiceId id = new RemoteServiceId();
        id.setGroupId(RSPropertyResolver.getPropertyValue(annotation.groupId()));
        id.setResourceId(RSPropertyResolver.getPropertyValue(annotation.resourceId()));
        id.setResourceVersion(RSPropertyResolver.getPropertyValue(annotation.resourceVersion()));
        id.setServiceVersion(RSPropertyResolver.getPropertyValue(annotation.serviceVersion()));
        id.setServiceId(RSPropertyResolver.getPropertyValue(annotation.serviceId()));
        id.setUri(RSPropertyResolver.getPropertyValue(annotation.url()));
        id.setUuid(RSPropertyResolver.getPropertyValue(annotation.uuid()));
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
