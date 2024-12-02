
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
