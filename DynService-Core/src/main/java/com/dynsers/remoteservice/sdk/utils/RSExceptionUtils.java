package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.exceptions.RSException;
import com.dynsers.remoteservice.sdk.exceptions.RSInvocationException;

import java.lang.reflect.InvocationTargetException;

public class RSExceptionUtils {

    public static RSException getConcreteRSException(InvocationTargetException invocException) {
        if(RSException.class.isAssignableFrom(invocException.getTargetException().getClass())) {
            return (RSException) invocException.getTargetException().getClass().cast(invocException.getTargetException());
        }
        return new RSInvocationException(invocException.getTargetException());
    }
}
