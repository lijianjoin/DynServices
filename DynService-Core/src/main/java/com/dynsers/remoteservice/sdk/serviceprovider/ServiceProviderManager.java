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

package com.dynsers.remoteservice.sdk.serviceprovider;

import com.dynsers.remoteservice.annotations.RemoteService;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.enums.ServiceProviderTypes;
import com.dynsers.remoteservice.exceptions.RemoteServiceException;
import com.dynsers.remoteservice.interfaces.RemoteServiceRegistry;
import com.dynsers.remoteservice.annotations.ServiceProvider;
import com.dynsers.remoteservice.sdk.configuration.RemoteServiceProviderProperties;
import com.dynsers.remoteservice.exceptions.RemoteServiceRegisteException;
import com.dynsers.remoteservice.sdk.configuration.RemoteServicePropertyResolver;
import com.dynsers.remoteservice.sdk.serviceconsumer.RemoteServiceProxy;
import com.dynsers.remoteservice.sdk.sharedutils.SpringContextUtils;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@DependsOn("applicationContextProvider")
public class ServiceProviderManager {

    @Getter
    private final RemoteServiceId baseServiceId;

    private final ServiceProviderRegistrantService registrantService;

    @Value("${server.port}")
    private int serverPort;

    @RemoteService(
            groupId = "${remoteService.server.groupId}",
            resourceId = "${remoteService.server.resourceId}",
            resourceVersion = "${remoteService.server.resourceVersion}",
            serviceVersion = "${remoteService.server.serviceVersion}",
            serviceName = "${remoteService.server.serviceName}",
            serviceLocation = "${remoteService.server.serviceLocation}",
            url = "${remoteService.server.url}")
    private RemoteServiceRegistry remoteServiceRegister;

    private final RemoteServiceProviderProperties providerProperties;

    public ServiceProviderManager(
            ServiceProviderRegistrantService registrantService,
            @Qualifier("rsServiceProviderProperties") RemoteServiceProviderProperties providerProperties) {
        this.registrantService = registrantService;
        baseServiceId = new RemoteServiceId();
        this.providerProperties = providerProperties;
        baseServiceId.setGroupId(providerProperties.getGroupId());
        baseServiceId.setResourceVersion(providerProperties.getResourceVersion());
        baseServiceId.setResourceId(providerProperties.getResourceId());
        baseServiceId.setServiceLocation(providerProperties.getServiceLocation());
        try {
            initializerRemoteServiceRegistry();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RemoteServiceRegisteException(e);
        }
    }

    @PostConstruct
    protected void scanAndRegisterServiceProviders() {
        // Get all beans with annotation RemoteServiceProvider
        if (!isTestMode()) {
            Map<String, Object> beans = SpringContextUtils.getContext().getBeansWithAnnotation(ServiceProvider.class);
            beans.forEach((key, value) -> {
                var serviceId = new RemoteServiceId(this.getBaseServiceId());
                ServiceProvider provider = value.getClass().getAnnotation(ServiceProvider.class);
                serviceId.setUuid(
                        StringUtils.isEmpty(provider.uuid()) ? String.valueOf(UUID.randomUUID()) : provider.uuid());
                serviceId.setDetectionInterval(provider.detectionInterval());
                serviceId.setServiceVersion(provider.version());
                String url = getServiceProviderUrl();
                try {
                    serviceId.setServiceName(provider.serviceName());
                    if (StringUtils.isNotEmpty(provider.id())) {
                        var serviceIdStr = provider.id();
                        serviceId.setServiceId(serviceIdStr);
                        serviceId.setUri(url);
                        storeServiceProvider(serviceId, value, provider.type());
                    } else {
                        Class<?>[] interfaces = getFirstDefinedInterfaces(value.getClass());
                        for (Class<?> inter : interfaces) {
                            String interfaceName = inter.getName();
                            var newId = new RemoteServiceId(serviceId);
                            newId.setServiceId(interfaceName);
                            newId.setUri(url);
                            storeServiceProvider(newId, value, provider.type());
                        }
                    }
                } catch (RemoteServiceException | InterruptedException e) {
                    log.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    private Class<?>[] getFirstDefinedInterfaces(Class<?> classIn) {
        Class<?>[] interfaces = classIn.getInterfaces();
        var res = new Class<?>[0];
        if (interfaces.length == 0) {
            Class<?> superClass = classIn.getSuperclass();
            if (null != superClass) {
                res = getFirstDefinedInterfaces(superClass);
            }
        } else {
            res = interfaces;
        }
        return res;
    }

    private boolean isTestMode() {
        var appName = RemoteServicePropertyResolver.getPropertyValue("spring.profiles.active");
        return appName != null && appName.contains("test");
    }

    private void storeServiceProvider(final RemoteServiceId serviceId, final Object bean, ServiceProviderTypes type)
            throws InterruptedException {
        if (ServiceProviderTypes.REMOTESERVICEPROVIDER.equals(type)) {
            registrantService.addServiceId(serviceId);
            ServiceProviderContainer.storeServiceProvider(serviceId, bean);
        } else {
            ServiceProviderContainer.storeServiceProvider(serviceId, bean);
        }
    }

    private void initializerRemoteServiceRegistry() throws IllegalAccessException, NoSuchFieldException {
        var registry = ServiceProviderManager.class.getDeclaredField("remoteServiceRegister");
        RemoteServiceProxy.setField(this, registry);
        registrantService.setRemoteServiceRegister(remoteServiceRegister);
    }

    private String getServiceProviderUrl() {
        return String.format(
                "%s://%s/%s/%s/%s/",
                providerProperties.getProtocol(),
                providerProperties.getHostname(),
                providerProperties.getContextPath(),
                providerProperties.getResourceId(),
                providerProperties.getResourceVersion());
    }
}
