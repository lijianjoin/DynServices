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
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotRegisterException;
import com.dynsers.remoteservice.utils.RemoteServiceServiceIdUtils;
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
