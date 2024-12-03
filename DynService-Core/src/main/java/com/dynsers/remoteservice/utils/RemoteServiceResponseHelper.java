/*
 *  Copyright "2024", Jian Li
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
