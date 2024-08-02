package com.dynsers.remoteservice.sdk.container;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceServiceIdUtils;
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
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return services;
    }

    public void storeService(RemoteServiceId serviceId){
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
            if(null == services) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized(services) {
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
            if(null == services) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized(services) {
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
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
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
