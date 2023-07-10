package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class RemoteServiceContainer {

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
