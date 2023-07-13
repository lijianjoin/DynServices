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
package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class RemoteServiceContainer {

    /**
     * The key is Classname and parameter name
     */
    private static Map<String, RemoteServiceId> serviceIdContainer;

    public static void storeServiceProviderId(Object obj, String paramName, RemoteServiceId serviceId) {
        String key = calcualteContainerKey(obj, paramName);
        getRemoteServiceIdContainer().put(key, serviceId);
    }

    public static RemoteServiceId getRemoteServiceId(Object obj, String paramName) throws RSServiceNotFoundException {
        String key = calcualteContainerKey(obj, paramName);
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

    private static String calcualteContainerKey(Object obj, String param) {
        StringBuilder sb = new StringBuilder();
        sb.append(obj.getClass().getName());
        sb.append(".");
        sb.append(param);
        return sb.toString();
    }

    public static void storeRemoteService() {

    }
}
