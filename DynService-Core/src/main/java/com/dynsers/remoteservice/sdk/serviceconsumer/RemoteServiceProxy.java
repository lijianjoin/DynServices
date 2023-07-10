package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.annotations.RemoteService;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import com.dynsers.remoteservice.sdk.utils.RSServiceIdUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

public class RemoteServiceProxy {




    @SuppressWarnings("unchecked")
    public static <T> T simpleProxy(Class<? extends T> iface, InvocationHandler handler, Class<?>...otherIfaces) {
        Class<?>[] allInterfaces = Stream.concat(
                        Stream.of(iface),
                        Stream.of(otherIfaces))
                .distinct()
                .toArray(Class<?>[]::new);

        return (T) Proxy.newProxyInstance(iface.getClassLoader(),
                allInterfaces,
                handler);
    }

    public static <T> T passthroughProxy(Object ow, Class<? extends T> iface, RemoteServiceId serviceId) {
        RemoteServiceInvocationHandler handler = RemoteServiceInvocationHandlerPool.getInvocationHandler(ow, serviceId);
        if(handler == null) {
            handler = new RemoteServiceInvocationHandler(ow, iface, serviceId);
            RemoteServiceInvocationHandlerPool.storeInvocationHandler(handler);
        }
        return simpleProxy(iface, handler);
    }

    public static void setField(Object obj, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(RemoteService.class)) {
            Class<?> fieldType = field.getType();
            ReflectionUtils.makeAccessible(field);
            RemoteService remoteService = field.getAnnotation(RemoteService.class);
            RemoteServiceId id = RSServiceIdUtils.fromAnnotation(remoteService);
            if(StringUtils.isEmpty(id.getServiceId())) {
                id.setServiceId(field.getType().getName());
            }
            Object proxyInstance = RemoteServiceProxy.passthroughProxy(obj, fieldType, id);
            field.set(obj, proxyInstance);
        }
    }

    public static void setField(Object obj, Field field, RemoteServiceId id) throws IllegalAccessException {
            Class<?> fieldType = field.getType();
            ReflectionUtils.makeAccessible(field);
            if(StringUtils.isEmpty(id.getServiceId())) {
                id.setServiceId(field.getType().getName());
            }
            Object proxyInstance = RemoteServiceProxy.passthroughProxy(obj, fieldType, id);
            field.set(obj, proxyInstance);
    }
}
