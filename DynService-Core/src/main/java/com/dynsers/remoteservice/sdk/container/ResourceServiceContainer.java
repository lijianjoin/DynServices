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
public class ResourceServiceContainer {

    private Map<String, ServiceNameContainer> serviceContainer = new HashMap<>();


    public ServiceNameContainer getServices(String serviceKey) {
        return serviceContainer.get(serviceKey);
    }

    public void putServices(String serviceKey, ServiceNameContainer services) {
        serviceContainer.put(serviceKey, services);
    }

    public ServiceNameContainer getServices(RemoteServiceId serviceId) {
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer services;
        synchronized (serviceContainer) {
            services = serviceContainer.get(serviceKey);
            if (null == services) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return services;
    }

    public void storeService(RemoteServiceId serviceId){
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer serviceNameContainer;
        synchronized (serviceContainer) {
            serviceNameContainer = serviceContainer.get(serviceKey);
            if (null == serviceNameContainer) {
                serviceNameContainer = new ServiceNameContainer();
            }
            serviceContainer.put(serviceKey, serviceNameContainer);
            serviceNameContainer.storeServiceByUUID(serviceId);
        }
    }

    public List<RemoteServiceId> getAllServiceId() {
        List<RemoteServiceId> result = new LinkedList<>();
        serviceContainer.values().forEach(s -> result.addAll(s.getAllServiceId()));
        return result;
    }

    public List<RemoteServiceId> getAllServiceIdsInServiceId(RemoteServiceId serviceId) {
        List<RemoteServiceId> result = new LinkedList<>();
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer services;
        synchronized (serviceContainer) {
            services = serviceContainer.get(serviceKey);
            if(null == services) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized(services) {
            result.addAll(services.getAllServiceId());
        }
        return result;
    }


    public RemoteServiceId getServiceId(RemoteServiceId serviceId) {
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        synchronized (serviceContainer) {
            ServiceNameContainer res = this.serviceContainer.get(serviceKey);
            if (null == res) {
                throw new RemoteServiceServiceNotRegisterException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            return res.getServiceId(serviceId);
        }
    }

    public RemoteServiceId deleteServiceId(RemoteServiceId serviceId) {
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer services;
        RemoteServiceId result;
        synchronized (serviceContainer) {
            services = serviceContainer.get(serviceKey);
            if(null == services) {
                return null;
            }
        }
        synchronized (services) {
            result = services.deleteServiceId(serviceId);
        }
        return result;
    }

}
