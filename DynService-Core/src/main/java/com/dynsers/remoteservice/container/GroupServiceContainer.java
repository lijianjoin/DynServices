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

package com.dynsers.remoteservice.container;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.utils.RemoteServiceServiceIdUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class GroupServiceContainer {

    private Map<String, ResourceServiceContainer> resourceServiceContainer = new HashMap<>();

    public ResourceServiceContainer getResourceServices(String groupKey) {
        return resourceServiceContainer.get(groupKey);
    }

    public void putResourceServices(String groupKey, ResourceServiceContainer resService) {
        this.resourceServiceContainer.put(groupKey, resService);
    }

    public ResourceServiceContainer getResourceServices(RemoteServiceId serviceId) {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer services;
        synchronized (resourceServiceContainer) {
            services = resourceServiceContainer.get(groupKey);
            if (null == services) {
                throw new RemoteServiceServiceNotRegisterException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return services;
    }

    public void storeService(RemoteServiceId serviceId) {
        String groupResourceKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer resourceServices;
        synchronized (resourceServiceContainer) {
            resourceServices = resourceServiceContainer.get(groupResourceKey);
            if (null == resourceServices) {
                resourceServices = new ResourceServiceContainer();
            }
            resourceServiceContainer.put(groupResourceKey, resourceServices);
            resourceServices.storeService(serviceId);
        }
    }

    public List<RemoteServiceId> getAllServiceId() {
        List<RemoteServiceId> result = new LinkedList<>();
        synchronized (resourceServiceContainer) {
            resourceServiceContainer.values().forEach(s -> result.addAll(s.getAllServiceId()));
        }
        return result;
    }

    public List<RemoteServiceId> getAllServiceIdsInGroupResource(RemoteServiceId serviceId) {
        List<RemoteServiceId> result = new LinkedList<>();
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer services;
        synchronized (resourceServiceContainer) {
            services = resourceServiceContainer.get(groupKey);
            if (null == services) {
                throw new RemoteServiceServiceNotRegisterException(
                        "Service not found: " + RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized (services) {
            result.addAll(services.getAllServiceId());
        }
        return result;
    }

    public List<RemoteServiceId> getServiceIdsInOneGroupResourceService(RemoteServiceId serviceId) {
        List<RemoteServiceId> result = new LinkedList<>();
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer services;
        synchronized (resourceServiceContainer) {
            services = resourceServiceContainer.get(groupKey);
            if (null == services) {
                throw new RemoteServiceServiceNotRegisterException(
                        "Service not found: " + RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized (services) {
            result.addAll(services.getAllServiceIdsInServiceId(serviceId));
        }
        return result;
    }

    public RemoteServiceId getServiceId(RemoteServiceId serviceId) {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        synchronized (resourceServiceContainer) {
            ResourceServiceContainer res = this.resourceServiceContainer.get(groupKey);
            if (null == res) {
                throw new RemoteServiceServiceNotRegisterException(
                        "Service not found: " + RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            return res.getServiceId(serviceId);
        }
    }

    public RemoteServiceId deleteServiceId(RemoteServiceId serviceId) {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer services;
        synchronized (resourceServiceContainer) {
            services = this.resourceServiceContainer.get(groupKey);
            if (null == services) {
                return null;
            }
        }
        synchronized (services) {
            return services.deleteServiceId(serviceId);
        }
    }
}
