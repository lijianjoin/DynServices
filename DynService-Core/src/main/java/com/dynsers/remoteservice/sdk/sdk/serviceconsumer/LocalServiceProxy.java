package com.dynsers.remoteservice.sdk.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.annotations.ServiceProvider;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.sdk.sharedutils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;

@Slf4j
public class LocalServiceProxy {

    private LocalServiceProxy() {
    }

    public static void setField(Object obj, Field field, RemoteServiceId id) {
        ReflectionUtils.makeAccessible(field);
        Collection<?> beans = SpringContextUtils.getBeans(field.getType());

        try {
            for (Object bean : beans) {
                ServiceProvider serviceProviderInfo = bean.getClass().getAnnotation(ServiceProvider.class);
                if (serviceProviderInfo != null && isMatchingService(id, serviceProviderInfo, field.getType())) {
                    field.set(obj, bean);
                    return;
                }
            }
        } catch (IllegalAccessException e) {
            log.error("Unexpected Remote Service Critical Error: {}.", e.getMessage());
        }
    }

    private static boolean isMatchingService(RemoteServiceId id, ServiceProvider serviceProviderInfo, Class<?> fieldType) {
        return StringUtils.equals(serviceProviderInfo.version(), id.getServiceVersion()) &&
                StringUtils.equals(fieldType.getName(), id.getServiceId()) &&
                StringUtils.equals(serviceProviderInfo.serviceName(), id.getServiceName()) &&
                (StringUtils.isEmpty(id.getUuid()) || StringUtils.equals(serviceProviderInfo.uuid(), id.getUuid()));
    }
}
