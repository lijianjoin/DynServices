package com.dynsers.remoteservice.sdk.utils;

import com.dynsers.remoteservice.sdk.exceptions.RSRequestErrorException;
import com.dynsers.remoteservice.sdk.data.RSMethodRequest;
import com.dynsers.remoteservice.sdk.data.RSMethodResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;
import java.util.Arrays;

public class RSRequestHelper {

    public static ResponseEntity<RSMethodResponse> checkRequest(RSMethodRequest body, Class<?> inter) {
        ResponseEntity<RSMethodResponse> result = null;
        if (body == null) {
            RSRequestErrorException rsNullExcept = new RSRequestErrorException("Error: request is null");
            result = RSResposeHelper.createResponseEntityWithException(null, rsNullExcept, HttpStatus.BAD_REQUEST);
        } else if (StringUtils.isEmpty(body.getMethod())) {
            RSRequestErrorException rsNullExcept = new RSRequestErrorException("Error: Method name is empty");
            result = RSResposeHelper.createResponseEntityWithException(null, rsNullExcept, HttpStatus.BAD_REQUEST);
        } else {
            try {
                Method m = inter.getMethod(body.getMethod(), body.getParameterTypes());
            } catch (NoSuchMethodException e) {
                final String paramNames = getParamterTypesAsString(body.getParameterTypes());
                RSRequestErrorException rsNullExcept = new RSRequestErrorException(
                        String.format("Error: No such method: %s with parameters: %s", body.getMethod(), paramNames));
                result = RSResposeHelper.createResponseEntityWithException(null, rsNullExcept, HttpStatus.BAD_REQUEST);
            }
        }
        return result;
    }

    private static String getParamterTypesAsString(Class[] parameterTypes) {
        StringBuilder sb = new StringBuilder();

        if (parameterTypes != null) {
            Arrays.stream(parameterTypes).forEach(val -> {
                sb.append(val);
                sb.append(";");
            });
        }

        return sb.toString();

    }

}
