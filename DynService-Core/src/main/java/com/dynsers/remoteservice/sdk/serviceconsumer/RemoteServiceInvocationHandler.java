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


    public RemoteServiceInvocationHandler(Object ow, Class<?> inter, RemoteServiceId id) {
        owner = ow;
        interfaceClz = inter;
        serviceId = id;
        invoker = new RemoteServiceInvoker();
    }

    public void updateRemoteService(RemoteServiceId id) {
        this.serviceId.update(id);
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
