package com.dynsers.remoteservice.sdk.serviceconsumer;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class RemoteServiceCallBack implements ReflectionUtils.FieldCallback {

    Object bean;
    public RemoteServiceCallBack(Object bean) {
        this.bean = bean;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        RemoteServiceProxy.setField(bean, field);
    }
}
