/**
 * Author: Jian Li, jian.li1@sartorius.com
 **/
package com.dynsers.remoteservice.sdk.exceptions;

import lombok.Getter;

@Getter
public class RemoteServiceInvocationException extends RemoteServiceException {

    private final Throwable originalException;
    
    public RemoteServiceInvocationException(Throwable e) {
        super(e.getMessage());
        originalException = e;
    }
}
