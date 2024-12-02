/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotFoundException;
import com.dynsers.remoteservice.sdk.controller.ServiceProviderInvocationDispatcherController;
import com.dynsers.remoteservice.utils.RemoteServiceServiceIdUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class ServiceProviderContainer {

    private ServiceProviderContainer() {
    }

    private static final Object LOCK = new Object();
    private static Map<String, Map<String, Map<String, Pair<RemoteServiceId, Object>>>> container = null;

    public static void storeServiceProvider(final RemoteServiceId serviceId, final Object bean) throws RemoteServiceServiceAlreadyRegisterException {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, Pair<RemoteServiceId, Object>>> services = getContainer().computeIfAbsent(groupKey, k -> new HashMap<>());
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        synchronized (LOCK) {
            Map<String, Pair<RemoteServiceId, Object>> uuidSers = services.computeIfAbsent(serviceKey, k -> new HashMap<>());
            Object service = uuidSers.get(serviceId.getUuid());
            if (null != service) {
                throw new RemoteServiceServiceAlreadyRegisterException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidSers.put(serviceId.getUuid(), new MutablePair<>(serviceId, bean));
        }
    }

    public static Object getServiceProvider(RemoteServiceId serviceId) throws RemoteServiceServiceNotRegisterException {
        String groupKey = RemoteServiceServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, Pair<RemoteServiceId, Object>>> services = getContainer().get(groupKey);
        Object res;
        if (null == services) {
            throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
        String serviceKey = RemoteServiceServiceIdUtils.getServiceKey(serviceId);
        synchronized (LOCK) {
            Map<String, Pair<RemoteServiceId, Object>> uuidSers = services.get(serviceKey);
            if (null == uuidSers) {
                throw new RemoteServiceServiceNotRegisterException(RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            if (uuidSers.size() == 1) {
                res = uuidSers.values().iterator().next().getValue();
            } else {
                res = uuidSers.get(serviceId.getUuid()).getValue();
            }
            if (null == res) {
                throw new RemoteServiceServiceNotFoundException(
                        RemoteServiceServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return res;
    }

    public static Map<String, Map<String, Map<String, Pair<RemoteServiceId, Object>>>> getContainer() {
        Map<String, Map<String, Map<String, Pair<RemoteServiceId, Object>>>> localRef = container;
        if (localRef == null) {
            synchronized (ServiceProviderInvocationDispatcherController.class) {
                localRef = container;
                if (localRef == null) {
                    container = localRef = new HashMap<>();
                }
            }
        }
        return localRef;
    }

    public static Collection<RemoteServiceId> getAllRemoteServiceIds() {
        List<RemoteServiceId> res = new LinkedList<>();
        for (var entryGroupResource : container.entrySet()) {
            for (var entryService : entryGroupResource.getValue().entrySet()) {
                for (var entryUuid : entryService.getValue().entrySet()) {
                    res.add(entryUuid.getValue().getKey());
                }
            }
        }
        return res;
    }

}
