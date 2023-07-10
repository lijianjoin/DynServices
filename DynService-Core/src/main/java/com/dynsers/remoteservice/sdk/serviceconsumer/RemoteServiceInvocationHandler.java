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
package com.dynsers.remoteservice.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.exceptions.RSException;
import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

@Data
public class RemoteServiceInvocationHandler implements InvocationHandler {

    private final RemoteServiceId serviceId;
    private final Object owner;
    private final RemoteServiceInvoker invoker;
    private Class<?> interfaceClz;

    private static int nr = 0;


    public RemoteServiceInvocationHandler(Object ow, Class<?> inter, RemoteServiceId id) {
        owner = ow;
        interfaceClz = inter;
        serviceId = id;
        invoker = new RemoteServiceInvoker();
        System.out.println(String.format("Handler %d is created", nr++));
        System.out.println(ow.getClass().getSimpleName());
    }

    public void updateRemoteService(RemoteServiceId id) {
        this.serviceId.update(id);
    }

    public boolean hasOwnerAndRemoteSerivceId(Object ow, RemoteServiceId id) {
        if (!Objects.equals(serviceId, id)) return false;
        return Objects.equals(owner, ow);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            result = invoker.invokeRemoteService(serviceId, method, method.getParameterTypes(), args);
        } catch (RSException except) {
            throw except;
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RemoteServiceInvocationHandler that)) return false;
        if (!Objects.equals(serviceId, that.serviceId)) return false;
        return Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        int result = (serviceId != null ? serviceId.hashCode() : 0);
        result = 31 * result + (owner != null ? System.identityHashCode(owner) : 0);
        return result;
    }


}
