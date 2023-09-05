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
package com.dynsers.core.serviceconsumer;

import com.dynsers.core.data.RemoteServiceId;

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
            RemoteServiceInvocationHandler result = null;
            for (RemoteServiceInvocationHandler handler : getInvocationHandlerPool()) {
                if (handler.hasOwnerAndRemoteSerivceId(ow, serviceId)) {
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
