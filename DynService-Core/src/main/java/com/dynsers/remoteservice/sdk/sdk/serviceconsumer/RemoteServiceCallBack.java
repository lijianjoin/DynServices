/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.enums.RemoteServiceInitialization;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceInjectionException;
import com.dynsers.remoteservice.sdk.utils.RemoteServiceServiceIdUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class RemoteServiceCallBack implements ReflectionUtils.FieldCallback {

    private final Object bean;

    public RemoteServiceCallBack(Object bean) {
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(RemoteService.class)) {
            RemoteService remoteService = field.getAnnotation(RemoteService.class);
            if (RemoteServiceInitialization.SPRINGBEANINIT.equals(remoteService.initialization())) {
                ReflectionUtils.makeAccessible(field);
                RemoteServiceId id = RemoteServiceServiceIdUtils.fromAnnotation(remoteService);
                if (StringUtils.isEmpty(id.getServiceId())) {
                    id.setServiceId(field.getType().getName());
                }
                try {
                    RemoteServiceManager.getInstance().configService(bean, field.getName(), id);
                } catch (NoSuchFieldException e) {
                    throw new RemoteServiceInjectionException(e);
                }
            }
        }
    }
}
