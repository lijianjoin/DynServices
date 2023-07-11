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

    private final Map<String, Map<String, Map<String, RemoteServiceId>>> container = new HashMap<>();

    private static final Object LOCK = new Object();

    public ServiceIdContainer() {
    }

    public List<RemoteServiceId> getAllServiceId() {
        List<RemoteServiceId> result = new ArrayList<>();
        List<Map<String, Map<String, RemoteServiceId>>> groupServices = container.values().stream().toList();
        List<Map<String, RemoteServiceId>> services = new ArrayList<>();
        groupServices.forEach(k -> services.addAll(k.values()));
        services.forEach(s -> result.addAll(s.values()));
        return result;
    }

    public RemoteServiceId deleteServiceId(RemoteServiceId serviceId) {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services;
        RemoteServiceId result = null;
        synchronized (container) {
            services = container.get(groupKey);
            if(null == services) {
                return null;
            }
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        Map<String, RemoteServiceId> uuidSers;
        synchronized (services) {
            uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                return null;
            }
        }
        synchronized (uuidSers) {
            result = uuidSers.remove(serviceId.getUuid());

        }

        return result;
    }

    public void storeServiceId(RemoteServiceId serviceId) throws RSServiceAlreadyRegisterException {
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services;
        synchronized (container) {
            services = container.get(groupKey);
            if(null == services) {
                services = new HashMap<>();
            }
            container.put(groupKey, services);
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        Map<String, RemoteServiceId> uuidSers;
        synchronized (services) {
            uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                uuidSers = new HashMap<>();
            }
            services.put(serviceKey, uuidSers);
        }
        synchronized (uuidSers) {
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
        Map<String, Map<String, RemoteServiceId>> services;
        synchronized (container) {
            services = container.get(groupKey);
            if(null == services) {
                services = new HashMap<>();
            }
            container.put(groupKey, services);
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        Map<String, RemoteServiceId> uuidSers;
        synchronized (services) {
            uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                uuidSers = new HashMap<>();
            }
            services.put(serviceKey, uuidSers);
        }
        synchronized (uuidSers) {
            RemoteServiceId service = uuidSers.get(serviceId.getUuid());
            if(null == service) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            uuidSers.put(serviceId.getUuid(), serviceId);
        }
    }

    public RemoteServiceId getRemoteService(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        RemoteServiceId res = null;
        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services;
        synchronized (container) {
            services = container.get(groupKey);
            if(null == services) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        Map<String, RemoteServiceId> uuidSers;
        synchronized (services) {
            uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized (uuidSers) {
            res = uuidSers.get(serviceId.getUuid());
            if (null == res) {
                throw new RSServiceNotRegisterException(
                        RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        return res;
    }

    public List<RemoteServiceId> getServiceIdsInOneGroupResource(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        List<RemoteServiceId> resList = new ArrayList<>();

        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services;
        synchronized (container) {
            services = container.get(groupKey);
            if(null == services) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        synchronized(services) {
            for(Map<String, RemoteServiceId> map : services.values()) {
                resList.addAll(map.values());
            }
        }
        return resList;
    }

    public List<RemoteServiceId> getServiceIdsInOneGroupResourceService(RemoteServiceId serviceId) throws RSServiceNotRegisterException {
        List<RemoteServiceId> resList = new ArrayList<>();

        String groupKey = RSServiceIdUtils.getGroupResourceKey(serviceId);
        Map<String, Map<String, RemoteServiceId>> services;
        synchronized (container) {
            services = container.get(groupKey);
            if(null == services) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
        }
        String serviceKey = RSServiceIdUtils.getServiceKey(serviceId);
        Map<String, RemoteServiceId> uuidSers;
        synchronized (services) {
            uuidSers = services.get(serviceKey);
            if(null == uuidSers) {
                throw new RSServiceNotRegisterException(RSServiceIdUtils.getServiceIdAsPlainString(serviceId));
            }
            resList.addAll(uuidSers.values());
        }

        return resList;
    }


}
