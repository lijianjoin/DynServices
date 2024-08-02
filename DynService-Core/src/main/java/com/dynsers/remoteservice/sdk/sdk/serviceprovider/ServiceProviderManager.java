/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.serviceprovider;

import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import com.dynsers.remoteservice.sdk.annotations.ServiceProvider;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.enums.ServiceProviderTypes;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceRegisteException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceServiceAlreadyRegisterException;
import com.dynsers.remoteservice.sdk.interfaces.RemoteServiceRegistry;
import com.dynsers.remoteservice.sdk.sdk.configuration.RemoteServiceProviderProperties;
import com.dynsers.remoteservice.sdk.sdk.serviceconsumer.RemoteServiceProxy;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${server.port}")
    private int serverPort;

    @RemoteService(groupId = "${remoteService.server.groupId}",
            resourceId = "${remoteService.server.resourceId}",
            resourceVersion = "${remoteService.server.resourceVersion}",
            serviceVersion = "${remoteService.server.serviceVersion}",
            serviceName = "${remoteService.server.serviceName}",
            url = "${remoteService.server.url}")
    private RemoteServiceRegistry remoteServiceRegister;

    private final RemoteServiceProviderProperties providerProperties;

    public ServiceProviderManager(@Qualifier("rsServiceProviderProperties")
                                  RemoteServiceProviderProperties providerProperties) {
        baseServiceId = new RemoteServiceId();
        this.providerProperties = providerProperties;
        baseServiceId.setGroupId(providerProperties.getGroupId());
        baseServiceId.setResourceVersion(providerProperties.getResourceVersion());
        baseServiceId.setResourceId(providerProperties.getResourceId());
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
            Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ServiceProvider.class);
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
                        Class<?>[] interfaces = value.getClass().getInterfaces();
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

    private boolean isTestMode() {
        var appName = applicationContext.getEnvironment().
                getProperty("spring.profiles.active");
        return appName != null && appName.contains("test");
    }

    private void storeServiceProvider(final RemoteServiceId serviceId, final Object bean, ServiceProviderTypes type) throws InterruptedException {
        var registered = false;
        if (ServiceProviderTypes.REMOTESERVICEPROVIDER.equals(type)) {
            while (!registered) {
                try {
                    log.debug("Registering service: {}", serviceId);
                    remoteServiceRegister.registerServiceProvider(serviceId);
                    registered = true;
                } catch (RemoteServiceServiceAlreadyRegisterException except) {
                    var registeredId = remoteServiceRegister.getRemoteServiceId(serviceId);
                    registered = true;
                    if (!StringUtils.equals(registeredId.getUri(), serviceId.getUri())) {
                        throw new RemoteServiceServiceAlreadyRegisterException(String.format("Service is already registered with URI: %s", registeredId.getUri()));
                    }
                } catch (Exception except) {
                    log.error(except.getMessage(), except);
                }
                Thread.sleep(2000);
            }
            ServiceProviderContainer.storeServiceProvider(serviceId, bean);
        } else {
            ServiceProviderContainer.storeServiceProvider(serviceId, bean);
        }
    }

    private void initializerRemoteServiceRegistry() throws IllegalAccessException, NoSuchFieldException {
        var registry = ServiceProviderManager.class.getDeclaredField("remoteServiceRegister");
        RemoteServiceProxy.setField(this, registry);
    }

    private String getServiceProviderUrl() {
        return String.format("http://%s:%d/%s/%s/%s/",
                providerProperties.getHostname(),
                serverPort,
                providerProperties.getContextPath(),
                providerProperties.getResourceId(),
                providerProperties.getResourceVersion());
    }
}
