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
import com.dynsers.core.utils.RSServiceIdUtils;
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
