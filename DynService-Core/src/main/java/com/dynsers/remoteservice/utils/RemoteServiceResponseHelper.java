/*

* Author: Jian Li, jian.li1@sartorius.com

*/
package com.dynsers.remoteservice.utils;

import com.dynsers.remoteservice.exceptions.RemoteServiceException;
import com.dynsers.remoteservice.data.RemoteServiceMethodResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RemoteServiceResponseHelper {

    public static ResponseEntity<String> createResponseEntityWithException(
            Object result, RemoteServiceException remoteServiceException, HttpStatus status)
            throws JsonProcessingException {
        var resp = new RemoteServiceMethodResponse();
        resp.setException(remoteServiceException);
        resp.setResult(SerializableConverterUtils.convertObjectToSerializable(result));
        resp.setStatus(status.value());
        resp.setExceptionType(remoteServiceException.getClass());
        var json = new ObjectMapper().writeValueAsString(resp);
        return new ResponseEntity<>(json, status);
    }
}
