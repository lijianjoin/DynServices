package com.dynsers.remoteservice.sdk.exceptions;

import lombok.Getter;

public class RemoteServiceClassNotFoundInDeserialize extends RemoteServiceException {

    @Getter
    private final Throwable originalException;

    public RemoteServiceClassNotFoundInDeserialize(Throwable e) {
        super(e.getMessage());
        originalException = e;
    }

}
