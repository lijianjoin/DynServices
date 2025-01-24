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

import com.dynsers.remoteservice.annotations.RemoteService;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.interfaces.RemoteServiceRegistry;
import com.dynsers.remoteservice.enums.ServiceProviderLocation;
import com.dynsers.remoteservice.exceptions.RemoteServiceInvocationException;
import com.dynsers.remoteservice.utils.RemoteServiceServiceIdUtils;
import io.micrometer.common.util.StringUtils;

import java.lang.reflect.Field;


public class RemoteServiceManager {

    private static RemoteServiceManager instance;

    @RemoteService(groupId = "${remoteService.server.groupId}",
            resourceId = "${remoteService.server.resourceId}",
            resourceVersion = "${remoteService.server.resourceVersion}",
            serviceVersion = "${remoteService.server.serviceVersion}",
            serviceName = "${remoteService.server.serviceName}",
            url = "${remoteService.server.url}")
    private RemoteServiceRegistry remoteServiceRegister;

    public static RemoteServiceManager getInstance() {
        RemoteServiceManager localRef = instance;
        if (localRef == null) {
            synchronized (RemoteServiceManager.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new RemoteServiceManager();
                    try {
                        var registry = localRef.getClass().getDeclaredField("remoteServiceRegister");
                        RemoteServiceProxy.setField(localRef, registry);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        throw new RemoteServiceInvocationException(e);
                    }
                }
            }
        }
        return localRef;
    }

    /**
     * Configure the parameter, whose name is paramName to use the serviceId
     *
     * @param owner     the owner
     * @param paramName the  parameter name
     * @param serviceId the service id
     */
    public void configService(Object owner, String paramName, RemoteServiceId serviceId) throws NoSuchFieldException {
        var field = owner.getClass().getDeclaredField(paramName);
        if (field.isAnnotationPresent(RemoteService.class)) {
            if (serviceId.getLocation() == ServiceProviderLocation.REMOTE) {
                configRemoteService(owner, field, serviceId);
            } else if (serviceId.getLocation() == ServiceProviderLocation.LOCAL) {
                configServiceLocal(owner, field, serviceId);
            }
        }
    }

    public void configService(Object owner, String paramName, ServiceProviderLocation location) throws NoSuchFieldException {
        var field = owner.getClass().getDeclaredField(paramName);
        if (field.isAnnotationPresent(RemoteService.class)) {
            RemoteService remoteService = field.getAnnotation(RemoteService.class);
            RemoteServiceId id = RemoteServiceServiceIdUtils.fromAnnotation(remoteService);
            if (location == ServiceProviderLocation.REMOTE) {
                configRemoteService(owner, field, id);
            } else if (location == ServiceProviderLocation.LOCAL) {
                configServiceLocal(owner, field, id);
            }
        }
    }

    /**
     * Configure the parameter, whose name is paramName to use the serviceId
     *
     * @param owner     the owner
     * @param paramName the parameter name
     */
    public void configService(Object owner, String paramName) throws NoSuchFieldException {
        var field = owner.getClass().getDeclaredField(paramName);
        if (field.isAnnotationPresent(RemoteService.class)) {
            RemoteService remoteService = field.getAnnotation(RemoteService.class);
            RemoteServiceId id = RemoteServiceServiceIdUtils.fromAnnotation(remoteService);
            if (id.getLocation() == ServiceProviderLocation.REMOTE) {
                configRemoteService(owner, field, id);
            } else if (id.getLocation() == ServiceProviderLocation.LOCAL) {
                configServiceLocal(owner, field, id);
            }
        }
    }

    private void configRemoteService(Object owner, Field field, RemoteServiceId serviceId) {
        if (StringUtils.isEmpty(serviceId.getServiceId())) {
            serviceId.setServiceId(field.getType().getName());
        }
        var handler = RemoteServiceInvocationHandlerPool.getInvocationHandler(serviceId);
        if (StringUtils.isEmpty(serviceId.getUri())) {
            var requestId = remoteServiceRegister.getRemoteServiceId(serviceId);
            serviceId.setUri(requestId.getUri());
        }
        if (null != handler) {
            // Here the rsId is a temporary id, and it is searched in the container
            // If it is not found, that means the service is not registered yet
            var rsId = RemoteServiceContainer.getRemoteServiceId(owner, field.getName());
            if (rsId == null) {
                injectParameter(owner, field, serviceId);
            }
            handler.updateRemoteService(serviceId);
            RemoteServiceContainer.storeServiceProviderId(owner, field.getName(), serviceId);
        } else {
            injectParameter(owner, field, serviceId);
            RemoteServiceContainer.storeServiceProviderId(owner, field.getName(), serviceId);
        }
    }

    private void configServiceLocal(Object owner, Field field, RemoteServiceId serviceId) {
        if (StringUtils.isEmpty(serviceId.getServiceId())) {
            serviceId.setServiceId(field.getType().getName());
        }
        LocalServiceProxy.setField(owner, field, serviceId);
    }

    private void injectParameter(Object owner, Field field, RemoteServiceId serviceId) {
        RemoteServiceProxy.setField(owner, field, serviceId);
    }
}
