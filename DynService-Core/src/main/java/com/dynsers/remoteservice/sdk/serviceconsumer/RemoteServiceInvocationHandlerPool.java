/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.data.RemoteServiceId;

import java.util.HashSet;
import java.util.Set;

public class RemoteServiceInvocationHandlerPool {

    private RemoteServiceInvocationHandlerPool() {
    }

    private static Set<RemoteServiceInvocationHandler> invocationHandlerPool = null;

    public static Set<RemoteServiceInvocationHandler> getInvocationHandlerPool() {
        Set<RemoteServiceInvocationHandler> localRef = invocationHandlerPool;
        if (localRef == null) {
            synchronized (RemoteServiceInvocationHandlerPool.class) {
                localRef = invocationHandlerPool;
                if (localRef == null) {
                    invocationHandlerPool = localRef = new HashSet<>();
                }
            }
        }
        return localRef;
    }

    public static RemoteServiceInvocationHandler getInvocationHandler(RemoteServiceId serviceId) {
        synchronized (RemoteServiceInvocationHandlerPool.class) {
            RemoteServiceInvocationHandler result = null;
            for (RemoteServiceInvocationHandler handler : getInvocationHandlerPool()) {
                if (handler.hasOwnerAndRemoteServiceId(serviceId)) {
                    result = handler;
                    break;
                }
            }
            return result;
        }
    }

    public static void storeInvocationHandler(RemoteServiceInvocationHandler handler) {
        synchronized (RemoteServiceInvocationHandlerPool.class) {
            getInvocationHandlerPool().add(handler);
        }
    }
}
