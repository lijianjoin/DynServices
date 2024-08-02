package com.dynsers.remoteservice.sdk.container;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceServiceIdUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class ServiceNameContainer {


    private Map<String, RemoteServiceId> uuidSericeContainer = new HashMap<>();


    public RemoteServiceId removeServiceId(String uuid) {
        return uuidSericeContainer.remove(uuid);
    }

    public RemoteServiceId getServiceId(String uuid) {
        return uuidSericeContainer.get(uuid);
    }

    public RemoteServiceId putServiceId(String uuid, RemoteServiceId serviceId) {
        return uuidSericeContainer.put(uuid, serviceId);
    }

    public void storeServiceByUUID(RemoteServiceId serviceId) throws RemoteServiceServiceAlreadyRegisterException {
        synchronized (uuidSericeContainer) {
            RemoteServiceId service = uuidSericeContainer.get(serviceId.getUuid());
            if (null != service) {
                throw new RemoteServiceServiceAlreadyRegisterException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidSericeContainer.put(serviceId.getUuid(), serviceId);
        }
    }

    public List<RemoteServiceId> getAllServiceId() {
        List<RemoteServiceId> result = new LinkedList<>();
        result.addAll(uuidSericeContainer.values());
        return result;
    }

    public int getServiceIdSize() {
        return uuidSericeContainer.size();
    }

    public RemoteServiceId getServiceId(RemoteServiceId serviceId) {
        synchronized (uuidSericeContainer) {
            RemoteServiceId res = uuidSericeContainer.get(serviceId.getUuid());
            if (null == res) {
                throw new RemoteServiceServiceNotRegisterException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            return res;
        }
    }

    public RemoteServiceId deleteServiceId(RemoteServiceId serviceId) {
        RemoteServiceId result;
        synchronized (uuidSericeContainer) {
            result = uuidSericeContainer.remove(serviceId.getUuid());
        }
        return result;
    }
}
