package com.dynsers.remoteservice.sdk.exceptions;

public abstract class RSException extends RuntimeException{

    public RSException(final String msg) {
        super(msg);
    }
}
