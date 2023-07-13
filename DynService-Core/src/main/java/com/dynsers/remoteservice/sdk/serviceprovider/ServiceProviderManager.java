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
package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import com.dynsers.remoteservice.sdk.annotations.ServiceProvider;
import com.dynsers.remoteservice.sdk.configuration.RSProperties;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.enums.ServerProviderTypes;
import com.dynsers.remoteservice.sdk.exceptions.RSException;
import com.dynsers.remoteservice.sdk.exceptions.RSServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.interfaces.RemoteServiceRegister;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceProxy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

public class ServiceProviderManager {

    private static ServiceProviderManager instance;

    private final RSProperties properties = RSProperties.getInstance();

    private final RemoteServiceId baseServiceId;

    @RemoteService(groupId = "${remoteService.server.groupId}",
            resourceId = "${remoteService.server.resourceId}",
            resourceVersion = "${remoteService.server.resourceVersion}",
            serviceVersion = "${remoteService.server.serviceVersion}",
            url = "${remoteService.server.url}")
    private RemoteServiceRegister remoteServiceRegister;

    private ServiceProviderManager() {
        baseServiceId = new RemoteServiceId();
        baseServiceId.setGroupId(properties.getServiceProviderGroupId());
        baseServiceId.setResourceVersion(properties.getServiceProviderResourceVersion());
        baseServiceId.setResourceId(properties.getServiceProviderResourceId());
        baseServiceId.setUri(properties.getServerUrl());
        try {
            initializerRemoteServiceRegistry();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static ServiceProviderManager getInstance() {
        ServiceProviderManager localRef = instance;
        if (localRef == null) {
            synchronized (ServiceProviderManager.class) {
                localRef = instance;
                if (localRef == null) {
                    instance = localRef = new ServiceProviderManager();
                }
            }
        }
        return localRef;
    }

    protected void scanAndRegisterServiceProviders(ConfigurableApplicationContext context) {
        // Get all beans with annotation RemoteServiceProvider
        Map<String, Object> beans = context.getBeansWithAnnotation(ServiceProvider.class);
        beans.forEach((key, value) -> {
            RemoteServiceId serviceId = new RemoteServiceId(this.getBaseServiceId());
            ServiceProvider provider = value.getClass().getAnnotation(ServiceProvider.class);
            serviceId.setUuid(StringUtils.isEmpty(provider.uuid()) ? String.valueOf(UUID.randomUUID()) : provider.uuid());
            serviceId.setServiceVersion(provider.version());
            String url =  getServiceProviderUrl();
            try {
                if (StringUtils.isNotEmpty(provider.name())) {
                    String serviceName = provider.name();
                    serviceId.setServiceId(serviceName);
                    serviceId.setUri(url);
                    storeServiceProvider(serviceId, value, provider.type());
                } else {
                    Class<?>[] interfaces = value.getClass().getInterfaces();
                    for (Class<?> inter : interfaces) {
                        String interName = inter.getName();
                        RemoteServiceId newId = new RemoteServiceId(serviceId);
                        newId.setServiceId(interName);
                        newId.setUri(url);
                        storeServiceProvider(newId, value, provider.type());
                    }
                }
            } catch (RSException except) {
                except.printStackTrace();
            }
        });
    }

    private void storeServiceProvider(final RemoteServiceId serviceId, final Object bean, ServerProviderTypes type) {
        if (ServerProviderTypes.REMOTESERVICEPROVIDER.equals(type)) {
            try {
                remoteServiceRegister.registerServiceProvider(serviceId);
            }
            catch (RSServiceAlreadyRegisterException except) {
                RemoteServiceId registeredId = remoteServiceRegister.getRemoteServiceId(serviceId);
                if(!StringUtils.equals(registeredId.getUri(), serviceId.getUri())) {
                    throw new RSServiceAlreadyRegisterException(String.format("Service is already registered with URI: %s", registeredId.getUri()));
                }
            }
        }
        ServiceProviderContainer.storeServiceProvider(serviceId, bean);
    }

    private void initializerRemoteServiceRegistry() throws IllegalAccessException, NoSuchFieldException {
        Field registry = ServiceProviderManager.class.getDeclaredField("remoteServiceRegister");
        RemoteServiceProxy.setField(this, registry);
    }

    public RemoteServiceId getBaseServiceId() {
        return baseServiceId;
    }

    private String getServiceProviderUrl() {
        final String url = String.format("http://%s:%d/%s/%s/%s/",
                properties.getServiceProviderHostname(),
                properties.getServiceProviderPort(),
                properties.getServiceProviderContextPath(),
                properties.getServiceProviderResourceId(),
                properties.getServiceProviderResourceVersion());
        return url;
    }
}
