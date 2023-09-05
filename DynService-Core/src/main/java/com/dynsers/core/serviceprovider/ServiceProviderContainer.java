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
package com.dynsers.core.serviceprovider;

import com.dynsers.core.data.RemoteServiceId;
import com.dynsers.core.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.core.exceptions.RSServiceNotFoundException;
import com.dynsers.core.exceptions.RSServiceNotRegisterException;
import com.dynsers.core.utils.RSServiceIdUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceProviderContainer {

    private static Map<String, Map<String, Map<String, Pair<RemoteServiceId, Object>>>> CONTAINER = null;

    private static final Object LOCK = new Object();

    public static void storeServiceProvider(final RemoteServiceId serviceId, final Object bean) throws RSServiceAlreadyRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, Pair<RemoteServiceId, Object>>> services = getContainer().computeIfAbsent(groupKey, k -> new HashMap<>());
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, Pair<RemoteServiceId, Object>> uuidSers = services.computeIfAbsent(serviceKey, k -> new HashMap<>());
            Object service = uuidSers.get(serviceId.getUuid());
            if (null != service) {
                throw new RSServiceAlreadyRegisterException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidSers.put(serviceId.getUuid(), new MutablePair<>(serviceId, bean));
        }
    }

    public static Object getServiceProvider(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, Pair<RemoteServiceId, Object>>> services = getContainer().get(groupKey);
        Object res = null;
        if(null == services) {
            throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, Pair<RemoteServiceId, Object>> uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            if(uuidSers.size() == 1) {
                res = uuidSers.values().iterator().next().getValue();
            }
            else {
                res = uuidSers.get(serviceId.getUuid()).getValue();
            }
            if (null == res) {
                throw new RSServiceNotFoundException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return res;
    }

    public static Map<String, Map<String, Map<String, Pair<RemoteServiceId, Object>>>> getContainer() {
        Map<String, Map<String, Map<String, Pair<RemoteServiceId, Object>>>> localRef = CONTAINER;
        if (localRef == null) {
            synchronized (ServiceProviderInvocationDispatcher.class) {
                localRef = CONTAINER;
                if (localRef == null) {
                    CONTAINER = localRef = new HashMap<>();
                }
            }
        }
        return localRef;
    }

    public static List<RemoteServiceId> getAllRemoteServiceId() {
        List<RemoteServiceId> res = new ArrayList<>();
        for(var entryGroupResource : CONTAINER.entrySet()) {
            for (var entryService : entryGroupResource.getValue().entrySet()) {
                for (var entryUuid : entryService.getValue().entrySet()) {
                    res.add(entryUuid.getValue().getKey());
                }
            }
        }
        return res;
    }


}
