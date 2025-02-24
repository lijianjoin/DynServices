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

import com.dynsers.DynService.core.api.annotation.RemoteService;
import com.dynsers.remoteservice.data.RemoteServiceId;
import com.dynsers.remoteservice.utils.RemoteServiceServiceIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.stream.Stream;

@Slf4j
public class RemoteServiceProxy {

    private RemoteServiceProxy() {}

    @SuppressWarnings("unchecked")
    public static <T> T simpleProxy(Class<? extends T> iface, InvocationHandler handler, Class<?>... otherIfaces) {
        Class<?>[] allInterfaces = Stream.concat(Stream.of(iface), Stream.of(otherIfaces))
                .distinct()
                .toArray(Class<?>[]::new);

        return (T) Proxy.newProxyInstance(iface.getClassLoader(), allInterfaces, handler);
    }

    public static <T> T passThroughProxy(Class<? extends T> iface, RemoteServiceId serviceId) {
        var handler = RemoteServiceInvocationHandlerPool.getInvocationHandler(serviceId);
        if (handler == null) {
            handler = new RemoteServiceInvocationHandler(iface, serviceId);
            RemoteServiceInvocationHandlerPool.storeInvocationHandler(handler);
        }
        return simpleProxy(iface, handler);
    }

    public static void setField(Object obj, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(RemoteService.class)) {
            Class<?> fieldType = field.getType();
            ReflectionUtils.makeAccessible(field);
            RemoteService remoteService = field.getAnnotation(RemoteService.class);
            RemoteServiceId id = RemoteServiceServiceIdUtils.fromAnnotation(remoteService);
            if (StringUtils.isEmpty(id.getServiceId())) {
                id.setServiceId(field.getType().getName());
            }
            Object proxyInstance = RemoteServiceProxy.passThroughProxy(fieldType, id);
            field.set(obj, proxyInstance);
        }
    }

    public static void setField(Object obj, Field field, RemoteServiceId id) {
        Class<?> fieldType = field.getType();
        ReflectionUtils.makeAccessible(field);
        Object proxyInstance = RemoteServiceProxy.passThroughProxy(fieldType, id);
        try {
            field.set(obj, proxyInstance);
        } catch (IllegalAccessException e) {
            log.error("setFields > Unexcoected Remote Service Critical Error: {}.", e.getMessage());
        }
    }
}
