/*

Copyright Jian Li, lijianjoin@gmail.com,

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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
