package com.dynsers.remoteservice.sdk.exceptions;

public class RSInvocationException extends RSException{

    public RSInvocationException(Exception e) {
        super(e.getMessage());
    }

    public Throwable originalException;

    public RSInvocationException(Throwable e) {
        super(e.getMessage());
        originalException = e;
    }

    public RSInvocationException(String msg) {
        super(msg);
    }

    public Throwable getOriginalException () {
        return originalException;
    }
}
