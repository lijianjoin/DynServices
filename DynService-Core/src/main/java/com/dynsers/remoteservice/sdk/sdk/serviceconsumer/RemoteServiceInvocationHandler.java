/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.sdk.serviceconsumer;

import com.dynsers.remoteservice.sdk.data.RemoteServiceId;
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
