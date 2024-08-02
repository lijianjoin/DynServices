package com.dynsers.remoteservice.sdk.exceptions;

import lombok.Getter;

@Getter
public class RemoteServiceReflectionException extends RemoteServiceException {

    private final Throwable originalException;

    public RemoteServiceReflectionException(Exception e) {
        super(e.getMessage());
        originalException = e;
    }

}
