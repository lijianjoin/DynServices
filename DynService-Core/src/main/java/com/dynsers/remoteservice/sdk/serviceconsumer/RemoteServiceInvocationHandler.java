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

import com.dynsers.remoteservice.data.RemoteServiceId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

@Data
@EqualsAndHashCode
@Slf4j
public class RemoteServiceInvocationHandler implements InvocationHandler {

    private static Integer nr = 0;
    private final RemoteServiceId serviceId;
    private final RemoteServiceInvoker invoker;
    private Class<?> interfaceClz;


    public RemoteServiceInvocationHandler(Class<?> inter, RemoteServiceId id) {
        this.interfaceClz = inter;
        this.serviceId = id;
        this.invoker = new RemoteServiceInvoker();
        log.debug("Handler {} is created for owner class: {}", nr++);
    }

    public void updateRemoteService(RemoteServiceId id) {
        this.serviceId.update(id);
    }

    public boolean hasOwnerAndRemoteServiceId(RemoteServiceId id) {
        return Objects.equals(serviceId, id);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(StringUtils.equals("toString", method.getName()))
            return this.getHandlerString();
        if(StringUtils.equals("equals", method.getName()))
            return StringUtils.equals(this.getHandlerString(), args[0].toString());
        if(StringUtils.equals("hashCode", method.getName()))
            return this.serviceId.hashCode();
        return invoker.invokeRemoteService(serviceId, method.getName(), method.getParameterTypes(), args);
    }

    private String getHandlerString(){
        return "RemoteServiceInvocationHandler-" + serviceId.toString();
    }

}
