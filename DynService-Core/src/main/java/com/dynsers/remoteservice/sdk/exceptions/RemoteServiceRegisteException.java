package com.dynsers.remoteservice.sdk.exceptions;

import lombok.Getter;

public class RemoteServiceRegisteException extends RemoteServiceException {

    @Getter
    private final Throwable originalException;

    public RemoteServiceRegisteException(Throwable e) {
        super(e.getMessage());
        originalException = e;
    }
}
