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

import com.dynsers.core.annotations.RemoteService;
import com.dynsers.core.data.RemoteServiceId;
import com.dynsers.core.interfaces.RemoteServiceRegister;
import io.micrometer.common.util.StringUtils;

import java.lang.reflect.Field;


public class RemoteServiceManager {

    private static RemoteServiceManager instance;

    @RemoteService(groupId = "${remoteService.server.groupId}",
            resourceId = "${remoteService.server.resourceId}",
            resourceVersion = "${remoteService.server.resourceVersion}",
            serviceVersion = "${remoteService.server.serviceVersion}",
            url = "${remoteService.server.url}")
    private RemoteServiceRegister remoteServiceRegister;

    public static RemoteServiceManager getInstance() {
        RemoteServiceManager localRef = instance;
        if (localRef == null) {
            synchronized (RemoteServiceManager.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RemoteServiceManager();
                    try {
                        injectRemoteService(localRef);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return localRef;
    }

    private static void injectRemoteService(Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            RemoteServiceProxy.setField(obj, field);
        }
    }

    /**
     * Configure all the interface which type is cls to use the serviceId
     *
     * @param owner
     * @param cls
     * @param serviceId
     */
    public void configRemoteService(Object owner, Class<?> cls, RemoteServiceId serviceId) {

    }

    /**
     * Configure the parameter, whose name is paramName to use the serivceId
     *
     * @param owner
     * @param paramName
     * @param serviceId
     */
    public void configRemoteService(Object owner, String paramName, RemoteServiceId serviceId) throws NoSuchFieldException, IllegalAccessException {

        Field field = owner.getClass().getDeclaredField(paramName);
        if (field.isAnnotationPresent(RemoteService.class)) {
            serviceId.setServiceId(field.getType().getName());
            RemoteServiceInvocationHandler handler = RemoteServiceInvocationHandlerPool.getInvocationHandler(owner, serviceId);
            if (StringUtils.isEmpty(serviceId.getUri())) {
                RemoteServiceId requestId = remoteServiceRegister.getRemoteServiceId(serviceId);
                serviceId.setUri(requestId.getUri());
            }
            if (null != handler) {
                RemoteServiceId rsId = RemoteServiceContainer.getRemoteServiceId(owner, paramName);
                if (rsId == null) {
                    injectParameter(owner, field, paramName, serviceId);
                }
                handler.updateRemoteService(serviceId);
                RemoteServiceContainer.storeServiceProviderId(owner, paramName, serviceId);
            } else {
                injectParameter(owner, field, paramName, serviceId);
            }
        }
    }

    private void injectParameter(Object owner, Field field, String paramName, RemoteServiceId serviceId) throws IllegalAccessException, NoSuchFieldException {
            RemoteServiceProxy.setField(owner, field, serviceId);
            RemoteServiceContainer.storeServiceProviderId(owner, paramName, serviceId);
    }
//    public void setRemoteServiceProvider(Class<?> cls, RemoteServiceId requestId) {

//    }


//    public RemoteServiceId getRemoteServiceProvider(RemoteServiceId requestId) {

//    }


//    public List<RemoteServiceId> getRemoteServiceProviders(Class<?> bean) {

//    }

//    public void setRemoteServiceProviders(Class<?> inter, RemoteServiceId) {

//    }
}
