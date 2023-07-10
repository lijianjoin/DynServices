package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;

import java.util.HashSet;
import java.util.Set;

public class RemoteServiceInvocationHandlerPool {

    private static Set<RemoteServiceInvocationHandler> REMOTESERVICE_INVOCATION_HANDLER_POOL = null;

    public static Set<RemoteServiceInvocationHandler> getInvocationHandlerPool() {
        Set<RemoteServiceInvocationHandler> localRef = REMOTESERVICE_INVOCATION_HANDLER_POOL;
        if (localRef == null) {
            synchronized (RemoteServiceInvocationHandlerPool.class) {
                localRef = REMOTESERVICE_INVOCATION_HANDLER_POOL;
                if (localRef == null) {
                    REMOTESERVICE_INVOCATION_HANDLER_POOL = localRef = new HashSet<>();
                }
            }
        }
        return localRef;
    }

    public static RemoteServiceInvocationHandler getInvocationHandler(Object ow, RemoteServiceId serviceId) {
        synchronized (RemoteServiceInvocationHandlerPool.class) {
            RemoteServiceInvocationHandler result = new RemoteServiceInvocationHandler(ow, null, serviceId);
            for (RemoteServiceInvocationHandler handler : getInvocationHandlerPool()) {
                if (handler.equals(result)) {
                    result = handler;
                    break;
                }
            }
            if (null == result.getInterfaceClz())
                result = null;
            return result;
        }
    }

    public static void storeInvocationHandler(RemoteServiceInvocationHandler handler) {
        synchronized (RemoteServiceInvocationHandlerPool.class) {
            getInvocationHandlerPool().add(handler);
        }
    }
}
