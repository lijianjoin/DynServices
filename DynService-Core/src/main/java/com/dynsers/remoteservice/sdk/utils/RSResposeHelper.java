package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.exceptions.RSException;
import com.dynsers.remoteservice.sdk.data.RSMethodResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RSResposeHelper {

    public static ResponseEntity<RSMethodResponse> createResponseEntityWithException(Object result, RSException rsException, HttpStatus status) {
        RSMethodResponse resp = new RSMethodResponse();
        resp.setException(rsException);
        resp.setResult(result);
        resp.setStatus(status.value());
        resp.setExceptionType(rsException.getClass());
        ResponseEntity<RSMethodResponse> respEntity = new ResponseEntity<>(resp, status);
        return respEntity;
    }
}
