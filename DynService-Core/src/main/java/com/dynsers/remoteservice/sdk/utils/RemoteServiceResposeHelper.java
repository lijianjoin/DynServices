/*

 * Author: Jian Li, jian.li1@sartorius.com

 */
package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.data.RemoteServiceMethodResponse;
import com.dynsers.remoteservice.sdk.exceptions.RemoteServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RemoteServiceResposeHelper {

    private RemoteServiceResposeHelper() {
    }

    public static ResponseEntity<RemoteServiceMethodResponse> createResponseEntityWithException(Object result, RemoteServiceException remoteServiceException, HttpStatus status) {
        var resp = new RemoteServiceMethodResponse();
        resp.setException(remoteServiceException);
        resp.setResult(SerializableConverterUtils.convertObjectToSerializable(result));
        resp.setStatus(status.value());
        resp.setExceptionType(remoteServiceException.getClass());
        return new ResponseEntity<>(resp, status);
    }
}
