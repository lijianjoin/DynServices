/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.exceptions.RemoteServiceException;
import com.dynsers.remoteservice.exceptions.RemoteServiceInvocationException;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RemoteServiceExceptionUtils {

    public static RemoteServiceException getConcreteRemoteServiceException(
            InvocationTargetException invocationException) {
        if (RemoteServiceException.class.isAssignableFrom(
                invocationException.getTargetException().getClass())) {
            return (RemoteServiceException)
                    invocationException.getTargetException().getClass().cast(invocationException.getTargetException());
        }
        return new RemoteServiceInvocationException(invocationException.getTargetException());
    }
}
