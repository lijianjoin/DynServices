/*

 * Author: Jian Li, jian.li1@sartorius.com

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
