package com.dynsers.remoteservice.sdk.container;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotFoundException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RSServiceIdUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceIdContainer {

    private Map<String, Map<String, Map<String, RemoteServiceId>>> container;

    private static final Object LOCK = new Object();

    public ServiceIdContainer() {
    }

    public void storeServiceId(RemoteServiceId serviceId) throws RSServiceAlreadyRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services = getContainer().computeIfAbsent(groupKey, k -> new HashMap<>());
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, RemoteServiceId> uuidSers = services.computeIfAbsent(serviceKey, k -> new HashMap<>());
            RemoteServiceId service = uuidSers.get(serviceId.getUuid());
            if (null != service) {
                throw new RSServiceAlreadyRegisterException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidSers.put(serviceId.getUuid(), serviceId);
        }
    }

    public void updateServiceId(RemoteServiceId serviceId) throws RSServiceAlreadyRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services = getContainer().computeIfAbsent(groupKey, k -> new HashMap<>());
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, RemoteServiceId> uuidSers = services.computeIfAbsent(serviceKey, k -> new HashMap<>());
            uuidSers.put(serviceId.getUuid(), serviceId);
        }
    }

    public RemoteServiceId getServiceIdWithUUID(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services = getContainer().get(groupKey);
        RemoteServiceId res = null;
        if(null == services) {
            throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, RemoteServiceId> uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
                res = uuidSers.get(serviceId.getUuid());
            if (null == res) {
                throw new RSServiceNotFoundException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return res;
    }

    public List<RemoteServiceId> getServiceIdsInOneGroupResource(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        List<RemoteServiceId> resList = new ArrayList<>();
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services = getContainer().get(groupKey);
        if(null == services) {
            throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
        synchronized(LOCK) {
            for(Map<String, RemoteServiceId> map : services.values()) {
                resList.addAll(map.values());
            }
        }
        return resList;
    }

    public List<RemoteServiceId> getServiceIdsInOneGroupResourceService(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        List<RemoteServiceId> resList = new ArrayList<>();
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services = getContainer().get(groupKey);
        if(null == services) {
            throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, RemoteServiceId> uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            resList.addAll(uuidSers.values());
        }
        return resList;
    }
    public Map<String, Map<String, Map<String, RemoteServiceId>>> getContainer() {
        Map<String, Map<String, Map<String, RemoteServiceId>>> localRef = container;
        if (localRef == null) {
            synchronized (ServiceIdContainer.class) {
                localRef = container;
                if (localRef == null) {
                    container = localRef = new HashMap<>();
                }
            }
        }
        return localRef;
    }


}
