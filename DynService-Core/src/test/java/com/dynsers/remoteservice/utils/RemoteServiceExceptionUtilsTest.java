package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.exceptions.RemoteServiceException;
import com.dynsers.remoteservice.exceptions.RemoteServiceInjectionException;
import com.dynsers.remoteservice.exceptions.RemoteServiceInvocationException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class RemoteServiceExceptionUtilsTest {

    @Test
    void getConcreteRemoteServiceException_withNonRemoteServiceException_returnsRemoteServiceInvocationException() {
        Exception originalException = new Exception("Test exception");
        InvocationTargetException invocationException = new InvocationTargetException(originalException);

        RemoteServiceException result =
                RemoteServiceExceptionUtils.getConcreteRemoteServiceException(invocationException);

        assertInstanceOf(RemoteServiceInvocationException.class, result);

        RemoteServiceInvocationException invocationExceptionResult = (RemoteServiceInvocationException) result;
        assertEquals(originalException, ((RemoteServiceInvocationException) result).getOriginalException());
    }

    @Test
    void getConcreteRemoteServiceException_withNullTargetException_returnsRemoteServiceInvocationException() {

        RemoteServiceInjectionException testException =
                new RemoteServiceInjectionException(new Exception("Test exception"));
        InvocationTargetException invocationException = new InvocationTargetException(testException);
        RemoteServiceException result =
                RemoteServiceExceptionUtils.getConcreteRemoteServiceException(invocationException);

        assertInstanceOf(RemoteServiceInjectionException.class, result);
        assertNull(result.getCause());
    }
}
