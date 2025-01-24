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

package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.exceptions.RemoteServiceServiceNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class RemoteServiceContainer {

    private RemoteServiceContainer() {
    }

    /**
     * The key is Classname and parameter name
     */
    private static Map<String, RemoteServiceId> serviceIdContainer;

    public static void storeServiceProviderId(Object obj, String paramName, RemoteServiceId serviceId) {
        String key = calculateContainerKey(obj, paramName);
        getRemoteServiceIdContainer().put(key, serviceId);
    }

    public static RemoteServiceId getRemoteServiceId(Object obj, String paramName) throws RemoteServiceServiceNotFoundException {
        String key = calculateContainerKey(obj, paramName);
        return getRemoteServiceIdContainer().get(key);
    }

    private static Map<String, RemoteServiceId> getRemoteServiceIdContainer() {
        Map<String, RemoteServiceId> localRef = serviceIdContainer;
        if (localRef == null) {
            synchronized (RemoteServiceContainer.class) {
                localRef = serviceIdContainer;
                if (localRef == null) {
                    serviceIdContainer = localRef = new HashMap<>();
                }
            }
        }
        return localRef;
    }

    private static String calculateContainerKey(Object obj, String param) {
        return obj.getClass().getName() +
                "." +
                param;
    }
}
