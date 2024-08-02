/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.container;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceServiceIdUtils;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class ServiceIdContainer {

    private final GroupServiceContainer  container = new GroupServiceContainer();

    public List<RemoteServiceId> getAllServiceId() {
        List<RemoteServiceId> result = new ArrayList<>();
        List<ResourceServiceContainer> groupServices =
                container.getResourceServiceContainer().values().stream().toList();
        List<ServiceNameContainer> services = new ArrayList<>();
        groupServices.forEach(k -> services.addAll(k.getServiceContainer().values()));
        services.forEach(s -> result.addAll(s.getUuidSericeContainer().values()));
        return result;
    }

    public RemoteServiceId deleteServiceId(RemoteServiceId serviceId) {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer resourceServiceContainer;
        RemoteServiceId result = null;
        synchronized (container) {
            resourceServiceContainer = container.getResourceServices(groupKey);
            if(null == resourceServiceContainer) {
                return null;
            }
        }
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer uuidServiceContainer;
        synchronized (resourceServiceContainer) {
            uuidServiceContainer = resourceServiceContainer.getServices(serviceKey);
            if(null == uuidServiceContainer) {
                return null;
            }
        }
        synchronized (uuidServiceContainer) {
            result = uuidServiceContainer.removeServiceId(serviceId.getUuid());
        }
        return result;
    }

    public void storeServiceId(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer resourceServices;
        synchronized (container) {
            resourceServices = container.getResourceServices(groupKey);
            if(null == resourceServices) {
                resourceServices = new ResourceServiceContainer();
            }
            container.putResourceServices(groupKey, resourceServices);
        }
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer uuidSers;
        synchronized (resourceServices) {
            uuidSers = resourceServices.getServices(serviceKey);
            if(null == uuidSers) {
                uuidSers = new ServiceNameContainer();
            }
            resourceServices.putServices(serviceKey, uuidSers);
        }
        synchronized (uuidSers) {
            RemoteServiceId service = uuidSers.getServiceId(serviceId.getUuid());
            if (null != service) {
                throw new RemoteServiceServiceAlreadyRegisterException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidSers.putServiceId(serviceId.getUuid(), serviceId);
        }
    }

    public void updateServiceId(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer resourceServices;
        synchronized (container) {
            resourceServices = container.getResourceServices(groupKey);
            if(null == resourceServices) {
                resourceServices = new ResourceServiceContainer();
            }
            container.putResourceServices(groupKey, resourceServices);
        }
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer uuidServices;
        synchronized (resourceServices) {
            uuidServices = resourceServices.getServices(serviceKey);
            if(null == uuidServices) {
                uuidServices = new ServiceNameContainer();
            }
            resourceServices.putServices(serviceKey, uuidServices);
        }
        synchronized (uuidServices) {
            RemoteServiceId service = uuidServices.getServiceId(serviceId.getUuid());
            if(null == service) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidServices.putServiceId(serviceId.getUuid(), serviceId);
        }
    }

    public RemoteServiceId getRemoteService(RemoteServiceId serviceId) throws RemoteServiceServiceNotRegisterException {
        RemoteServiceId res = null;
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer resourceServices;
        synchronized (container) {
            resourceServices = container.getResourceServices(groupKey);
            if(null == resourceServices) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer uuidServices;
        synchronized (resourceServices) {
            uuidServices = resourceServices.getServices(serviceKey);
            if(null == uuidServices) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized (uuidServices) {
            if(uuidServices.getServiceIdSize() == 1) {
                res = uuidServices.getAllServiceId().get(0);
            }
            else {
                res = uuidServices.getServiceId(serviceId.getUuid());
                if (null == res) {
                    throw new RemoteServiceServiceNotRegisterException(
                            RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
                }
            }
        }
        return res;
    }

    public List<RemoteServiceId> getServiceIdsInOneGroupResource(RemoteServiceId serviceId) throws RemoteServiceServiceNotRegisterException {
        List<RemoteServiceId> resList = new LinkedList<>();

        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer resourceServices;
        synchronized (container) {
            resourceServices = container.getResourceServices(groupKey);
            if(null == resourceServices) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized(resourceServices) {
            resList.addAll(resourceServices.getAllServiceId());
        }
        return resList;
    }

    public List<RemoteServiceId> getServiceIdsInOneGroupResourceService(RemoteServiceId serviceId) throws RemoteServiceServiceNotRegisterException {
        List<RemoteServiceId> resList = new LinkedList<>();

        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        ResourceServiceContainer resourceServices;
        synchronized (container) {
            resourceServices = container.getResourceServices(groupKey);
            if(null == resourceServices) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        ServiceNameContainer uuidServices;
        synchronized (resourceServices) {
            uuidServices = resourceServices.getServices(serviceKey);
            if(null == uuidServices) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            resList.addAll(uuidServices.getAllServiceId());
        }
        return resList;
    }
}
