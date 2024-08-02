package com.dynsers.remoteservice.sdk.exceptions;

import lombok.Getter;

public class RemoteServiceInjectionException extends RemoteServiceException {

    @Getter
    private final Throwable originalException;

    public RemoteServiceInjectionException(Throwable e) {
        super(e.getMessage());
        originalException = e;
    }

}
