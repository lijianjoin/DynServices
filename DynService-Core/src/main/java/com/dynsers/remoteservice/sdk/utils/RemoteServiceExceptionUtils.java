/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.utils;


import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceException;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceInvocationException;

import java.lang.reflect.InvocationTargetException;

public class RemoteServiceExceptionUtils {

    private RemoteServiceExceptionUtils() {
    }

    public static RemoteServiceException getConcreteRemoteServiceException(InvocationTargetException invocationException) {
        if (RemoteServiceException.class.isAssignableFrom(invocationException.getTargetException().getClass())) {
            return (RemoteServiceException) invocationException.getTargetException().getClass().cast(invocationException.getTargetException());
        }
        return new RemoteServiceInvocationException(invocationException.getTargetException());
    }
}
