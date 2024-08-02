/*

 * Author: Jian Li, jian.li1@sartorius.com

 */

package com.dynsers.remoteservice.sdk.exceptions;

public abstract class RemoteServiceException extends RuntimeException {

    protected RemoteServiceException(final String msg) {
        super(msg);
    }
}
