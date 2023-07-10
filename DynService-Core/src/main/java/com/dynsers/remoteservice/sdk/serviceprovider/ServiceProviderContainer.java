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
package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotFoundException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotRegisterException;
import com.dynsers.remoteservice.sdk.utils.RSServiceIdUtils;

import java.util.HashMap;
import java.util.Map;

public class ServiceProviderContainer {

    private static Map<String, Map<String, Map<String,Object>>> CONTAINER = null;

    private static final Object LOCK = new Object();

    public static void storeServiceProvider(final RemoteServiceId serviceId, final Object bean) throws RSServiceAlreadyRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, Object>> services = getContainer().computeIfAbsent(groupKey, k -> new HashMap<>());
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, Object> uuidSers = services.computeIfAbsent(serviceKey, k -> new HashMap<>());
            Object service = uuidSers.get(serviceId.getUuid());
            if (null != service) {
                throw new RSServiceAlreadyRegisterException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidSers.put(serviceId.getUuid(), bean);
        }
    }

    public static Object getServiceProvider(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, Object>> services = getContainer().get(groupKey);
        Object res = null;
        if(null == services) {
            throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        synchronized(LOCK) {
            Map<String, Object> uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            if(uuidSers.size() == 1) {
                res = uuidSers.values().toArray()[0];
            }
            else {
                res = uuidSers.get(serviceId.getUuid());
            }
            if (null == res) {
                throw new RSServiceNotFoundException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return res;
    }

    public static Map<String, Map<String, Map<String,Object>>> getContainer() {
        Map<String, Map<String, Map<String,Object>>> localRef = CONTAINER;
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

}
